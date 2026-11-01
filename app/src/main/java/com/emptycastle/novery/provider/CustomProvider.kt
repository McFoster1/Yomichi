package com.emptycastle.novery.provider

import com.emptycastle.novery.domain.model.Chapter
import com.emptycastle.novery.domain.model.MainPageResult
import com.emptycastle.novery.domain.model.Novel
import com.emptycastle.novery.domain.model.NovelDetails
import com.emptycastle.novery.util.HtmlUtils
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.net.URI
import java.net.URLEncoder

/**
 * Generic provider driven by user-supplied CSS selectors.
 * Works for simple sites that serve their content as plain HTML.
 */
class CustomProvider(val config: CustomProviderConfig) : MainProvider() {

    override val name: String = config.name
    override val mainUrl: String = config.baseUrl.trim().trimEnd('/')
    override val hasMainPage: Boolean = config.browseUrl.isNotBlank()
    override val hasReviews: Boolean = false

    // ================================================================
    // HELPERS
    // ================================================================

    private fun resolveAgainst(base: String, href: String): String {
        val trimmed = href.trim()
        return when {
            trimmed.startsWith("http://") || trimmed.startsWith("https://") -> trimmed
            trimmed.startsWith("//") -> "https:$trimmed"
            trimmed.startsWith("/") -> "$mainUrl$trimmed"
            else -> try {
                URI(base).resolve(trimmed).toString()
            } catch (e: Exception) {
                fixUrl(trimmed) ?: trimmed
            }
        }
    }

    private fun templateUrl(template: String): String {
        val trimmed = template.trim()
        return if (trimmed.startsWith("http")) trimmed else (fixUrl(trimmed) ?: trimmed)
    }

    private suspend fun fetch(url: String): Document {
        val response = get(url)
        val document = response.document
        if (response.isCloudflareBlocked ||
            document.title().contains("Just a moment", ignoreCase = true) ||
            document.title().contains("Cloudflare", ignoreCase = true)
        ) {
            throw Exception("Cloudflare protection detected. This site can't be used as a custom provider.")
        }
        return document
    }

    private fun imageUrl(img: Element?, pageUrl: String): String? {
        if (img == null) return null
        val raw = img.attrOrNull("src")
            ?: img.attrOrNull("data-src")
            ?: img.attrOrNull("data-original")
            ?: img.attrOrNull("data-lazy-src")
            ?: return null
        if (raw.startsWith("data:")) return null
        return resolveAgainst(pageUrl, raw)
    }

    // ================================================================
    // NOVEL LISTS (browse + search)
    // ================================================================

    private fun parseNovelList(document: Document, pageUrl: String): List<Novel> {
        if (config.resultItemSelector.isBlank()) return emptyList()
        val linkSelector = config.resultLinkSelector.ifBlank { "a" }
        val coverSelector = config.resultCoverSelector.ifBlank { "img" }

        return document.select(config.resultItemSelector).mapNotNull { element ->
            val link = element.selectFirstOrNull(linkSelector)
                ?: (if (element.tagName() == "a") element else null)
                ?: return@mapNotNull null
            val href = link.attrOrNull("href") ?: return@mapNotNull null
            val title = link.textOrNull()
                ?: link.attrOrNull("title")
                ?: element.selectFirstOrNull("img")?.attrOrNull("alt")
                ?: return@mapNotNull null

            Novel(
                name = title,
                url = resolveAgainst(pageUrl, href),
                posterUrl = imageUrl(element.selectFirstOrNull(coverSelector), pageUrl),
                apiName = this.name
            )
        }.distinctBy { it.url }
    }

    override suspend fun loadMainPage(
        page: Int,
        orderBy: String?,
        tag: String?,
        extraFilters: Map<String, String>
    ): MainPageResult {
        if (config.browseUrl.isBlank()) return MainPageResult(url = mainUrl, novels = emptyList())
        val url = templateUrl(config.browseUrl.replace("{page}", page.toString()))
        val document = fetch(url)
        return MainPageResult(url = url, novels = parseNovelList(document, url))
    }

    override suspend fun search(query: String): List<Novel> {
        if (config.searchUrl.isBlank()) return emptyList()
        val encoded = URLEncoder.encode(query, "UTF-8")
        val url = templateUrl(config.searchUrl.replace("{query}", encoded))
        val document = fetch(url)
        return parseNovelList(document, url)
    }

    // ================================================================
    // NOVEL DETAILS + CHAPTER LIST
    // ================================================================

    override suspend fun load(url: String): NovelDetails? {
        val fullUrl = resolveAgainst(mainUrl, url)
        val document = fetch(fullUrl)

        val title = document.selectFirstOrNull(config.titleSelector.ifBlank { "h1" })
            ?.textOrNull() ?: "Unknown"

        val author = config.authorSelector.takeIf { it.isNotBlank() }
            ?.let { document.selectFirstOrNull(it)?.textOrNull() }

        val synopsis = config.synopsisSelector.takeIf { it.isNotBlank() }
            ?.let { document.selectFirstOrNull(it)?.textOrNull() }

        val poster = config.coverSelector.takeIf { it.isNotBlank() }
            ?.let { imageUrl(document.selectFirstOrNull(it), fullUrl) }

        var chapters: List<Chapter> = if (config.chapterLinkSelector.isBlank()) {
            emptyList()
        } else {
            document.select(config.chapterLinkSelector).mapNotNull { element ->
                val chapterName = element.textOrNull()
                    ?: element.attrOrNull("title")
                    ?: return@mapNotNull null
                val href = element.attrOrNull("href") ?: return@mapNotNull null
                Chapter(name = chapterName, url = resolveAgainst(fullUrl, href))
            }.distinctBy { it.url }
        }
        if (config.reverseChapters) chapters = chapters.reversed()

        return NovelDetails(
            url = fullUrl,
            name = title,
            chapters = chapters,
            author = author,
            posterUrl = poster,
            synopsis = synopsis ?: "No Summary Found"
        )
    }

    // ================================================================
    // CHAPTER TEXT
    // ================================================================

    override suspend fun loadChapterContent(url: String): String? {
        if (config.chapterContentSelector.isBlank()) return null
        val fullUrl = resolveAgainst(mainUrl, url)
        val document = fetch(fullUrl)

        val content = document.selectFirstOrNull(config.chapterContentSelector) ?: return null
        content.select("script, style, iframe, ins, .adsbygoogle").remove()

        content.select("img").forEach { img ->
            val absolute = imageUrl(img, fullUrl)
            if (absolute != null) img.attr("src", absolute)
        }

        return HtmlUtils.cleanChapterContent(content.html(), "").trim()
    }
}
