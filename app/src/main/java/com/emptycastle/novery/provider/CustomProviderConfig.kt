package com.emptycastle.novery.provider

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

/**
 * User-defined provider settings. Everything the generic [CustomProvider] needs
 * to scrape a simple HTML novel site.
 *
 * URL templates:
 *  - searchUrl may contain {query}
 *  - browseUrl may contain {page}
 */
data class CustomProviderConfig(
    val name: String,
    val baseUrl: String,
    val searchUrl: String = "",
    val browseUrl: String = "",
    val resultItemSelector: String = "",
    val resultLinkSelector: String = "a",
    val resultCoverSelector: String = "img",
    val titleSelector: String = "h1",
    val coverSelector: String = "",
    val synopsisSelector: String = "",
    val authorSelector: String = "",
    val chapterLinkSelector: String = "",
    val chapterContentSelector: String = "",
    val reverseChapters: Boolean = false
) {
    fun toJson(): JSONObject = JSONObject().apply {
        put("name", name)
        put("baseUrl", baseUrl)
        put("searchUrl", searchUrl)
        put("browseUrl", browseUrl)
        put("resultItemSelector", resultItemSelector)
        put("resultLinkSelector", resultLinkSelector)
        put("resultCoverSelector", resultCoverSelector)
        put("titleSelector", titleSelector)
        put("coverSelector", coverSelector)
        put("synopsisSelector", synopsisSelector)
        put("authorSelector", authorSelector)
        put("chapterLinkSelector", chapterLinkSelector)
        put("chapterContentSelector", chapterContentSelector)
        put("reverseChapters", reverseChapters)
    }

    companion object {
        fun fromJson(o: JSONObject): CustomProviderConfig = CustomProviderConfig(
            name = o.optString("name"),
            baseUrl = o.optString("baseUrl"),
            searchUrl = o.optString("searchUrl"),
            browseUrl = o.optString("browseUrl"),
            resultItemSelector = o.optString("resultItemSelector"),
            resultLinkSelector = o.optString("resultLinkSelector", "a"),
            resultCoverSelector = o.optString("resultCoverSelector", "img"),
            titleSelector = o.optString("titleSelector", "h1"),
            coverSelector = o.optString("coverSelector"),
            synopsisSelector = o.optString("synopsisSelector"),
            authorSelector = o.optString("authorSelector"),
            chapterLinkSelector = o.optString("chapterLinkSelector"),
            chapterContentSelector = o.optString("chapterContentSelector"),
            reverseChapters = o.optBoolean("reverseChapters", false)
        )
    }
}

/**
 * Saves custom providers in SharedPreferences as a JSON array.
 */
object CustomProviderStore {
    private const val PREFS = "yomichi_custom_providers"
    private const val KEY = "providers"

    fun loadAll(context: Context): List<CustomProviderConfig> {
        val raw = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getString(KEY, null) ?: return emptyList()
        return try {
            val array = JSONArray(raw)
            (0 until array.length())
                .map { CustomProviderConfig.fromJson(array.getJSONObject(it)) }
                .filter { it.name.isNotBlank() && it.baseUrl.isNotBlank() }
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun saveAll(context: Context, list: List<CustomProviderConfig>) {
        val array = JSONArray()
        list.forEach { array.put(it.toJson()) }
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit().putString(KEY, array.toString()).apply()
    }

    /** Add a new provider, or replace the one called [oldName]. */
    fun upsert(context: Context, oldName: String?, config: CustomProviderConfig) {
        val list = loadAll(context).toMutableList()
        val index = list.indexOfFirst { it.name == (oldName ?: config.name) }
        if (index >= 0) list[index] = config else list.add(config)
        saveAll(context, list)
    }

    fun delete(context: Context, name: String) {
        saveAll(context, loadAll(context).filter { it.name != name })
    }
}
