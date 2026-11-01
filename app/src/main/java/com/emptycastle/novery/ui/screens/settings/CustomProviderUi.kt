package com.emptycastle.novery.ui.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.emptycastle.novery.provider.CustomProviderConfig
import org.jsoup.Jsoup

/** Edit + delete icons shown on custom provider rows. */
@Composable
internal fun CustomProviderActions(onEdit: () -> Unit, onDelete: () -> Unit) {
    IconButton(onClick = onEdit, modifier = Modifier.size(32.dp)) {
        Icon(
            Icons.Outlined.Edit,
            contentDescription = "Edit provider",
            modifier = Modifier.size(18.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
    IconButton(onClick = onDelete, modifier = Modifier.size(32.dp)) {
        Icon(
            Icons.Outlined.Delete,
            contentDescription = "Delete provider",
            modifier = Modifier.size(18.dp),
            tint = MaterialTheme.colorScheme.error
        )
    }
}

@Composable
internal fun AddProviderButton(onClick: () -> Unit) {
    OutlinedButton(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        Icon(Icons.Outlined.Add, contentDescription = null, modifier = Modifier.size(18.dp))
        Text("  Add provider")
    }
}

@Composable
internal fun DeleteProviderDialog(name: String, onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Delete $name?") },
        text = {
            Text(
                "Novels already in your library from this provider stay there, " +
                    "but can't load until you add the provider back with the same name."
            )
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Delete", color = MaterialTheme.colorScheme.error)
            }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } }
    )
}

@Composable
private fun ConfigField(
    label: String,
    value: String,
    hint: String? = null,
    onChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = { Text(label) },
        supportingText = if (hint != null) { { Text(hint) } } else null,
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}

private fun isValidSelector(selector: String): Boolean {
    if (selector.isBlank()) return true
    return try {
        Jsoup.parse("<a></a>").select(selector)
        true
    } catch (e: Exception) {
        false
    }
}

/** Add / edit form for a custom provider. */
@Composable
internal fun CustomProviderDialog(
    initial: CustomProviderConfig?,
    takenNames: Set<String>,
    onDismiss: () -> Unit,
    onSave: (CustomProviderConfig) -> Unit
) {
    var name by remember { mutableStateOf(initial?.name ?: "") }
    var baseUrl by remember { mutableStateOf(initial?.baseUrl ?: "") }
    var searchUrl by remember { mutableStateOf(initial?.searchUrl ?: "") }
    var browseUrl by remember { mutableStateOf(initial?.browseUrl ?: "") }
    var resultItem by remember { mutableStateOf(initial?.resultItemSelector ?: "") }
    var resultLink by remember { mutableStateOf(initial?.resultLinkSelector ?: "a") }
    var resultCover by remember { mutableStateOf(initial?.resultCoverSelector ?: "img") }
    var titleSel by remember { mutableStateOf(initial?.titleSelector ?: "h1") }
    var coverSel by remember { mutableStateOf(initial?.coverSelector ?: "") }
    var synopsisSel by remember { mutableStateOf(initial?.synopsisSelector ?: "") }
    var authorSel by remember { mutableStateOf(initial?.authorSelector ?: "") }
    var chapterLinks by remember { mutableStateOf(initial?.chapterLinkSelector ?: "") }
    var chapterContent by remember { mutableStateOf(initial?.chapterContentSelector ?: "") }
    var reverse by remember { mutableStateOf(initial?.reverseChapters ?: false) }
    var error by remember { mutableStateOf<String?>(null) }

    fun validate(): String? {
        val n = name.trim()
        val base = baseUrl.trim()
        return when {
            n.isBlank() -> "Enter a name."
            n.contains(",") -> "The name can't contain a comma."
            n in takenNames -> "A provider called \"$n\" already exists."
            !(base.startsWith("http://") || base.startsWith("https://")) ->
                "Site address must start with http:// or https://"
            searchUrl.isBlank() && browseUrl.isBlank() ->
                "Fill in a search URL, a browse URL, or both."
            searchUrl.isNotBlank() && !searchUrl.contains("{query}") ->
                "Search URL must contain {query}."
            resultItem.isBlank() -> "Enter the selector for each novel in a list."
            chapterLinks.isBlank() -> "Enter the selector for chapter links."
            chapterContent.isBlank() -> "Enter the selector for the chapter text."
            listOf(
                resultItem, resultLink, resultCover, titleSel, coverSel,
                synopsisSel, authorSel, chapterLinks, chapterContent
            ).any { !isValidSelector(it) } -> "One of the CSS selectors isn't valid."
            else -> null
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (initial == null) "Add provider" else "Edit provider") },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    "For simple sites that load their pages as plain HTML. " +
                        "Sites with Cloudflare protection or JavaScript-built pages won't work.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                ConfigField("Name", name) { name = it }
                ConfigField("Site address", baseUrl, "e.g. https://example.com") { baseUrl = it }
                ConfigField(
                    "Search URL", searchUrl,
                    "Use {query}, e.g. /search?q={query}"
                ) { searchUrl = it }
                ConfigField(
                    "Browse URL (optional)", browseUrl,
                    "Use {page}, e.g. /popular?page={page}"
                ) { browseUrl = it }

                Text("Novel lists", style = MaterialTheme.typography.titleSmall)
                ConfigField("Novel item selector", resultItem, "CSS selector for each novel in a list") { resultItem = it }
                ConfigField("Link inside item", resultLink) { resultLink = it }
                ConfigField("Cover inside item", resultCover) { resultCover = it }

                Text("Novel page", style = MaterialTheme.typography.titleSmall)
                ConfigField("Title selector", titleSel) { titleSel = it }
                ConfigField("Cover selector (optional)", coverSel) { coverSel = it }
                ConfigField("Synopsis selector (optional)", synopsisSel) { synopsisSel = it }
                ConfigField("Author selector (optional)", authorSel) { authorSel = it }
                ConfigField("Chapter link selector", chapterLinks, "Selector for the <a> of each chapter") { chapterLinks = it }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "Reverse chapter order",
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Switch(checked = reverse, onCheckedChange = { reverse = it })
                }

                Text("Chapter page", style = MaterialTheme.typography.titleSmall)
                ConfigField("Chapter text selector", chapterContent) { chapterContent = it }

                error?.let {
                    Text(
                        it,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                val problem = validate()
                if (problem != null) {
                    error = problem
                } else {
                    onSave(
                        CustomProviderConfig(
                            name = name.trim(),
                            baseUrl = baseUrl.trim().trimEnd('/'),
                            searchUrl = searchUrl.trim(),
                            browseUrl = browseUrl.trim(),
                            resultItemSelector = resultItem.trim(),
                            resultLinkSelector = resultLink.trim().ifBlank { "a" },
                            resultCoverSelector = resultCover.trim().ifBlank { "img" },
                            titleSelector = titleSel.trim().ifBlank { "h1" },
                            coverSelector = coverSel.trim(),
                            synopsisSelector = synopsisSel.trim(),
                            authorSelector = authorSel.trim(),
                            chapterLinkSelector = chapterLinks.trim(),
                            chapterContentSelector = chapterContent.trim(),
                            reverseChapters = reverse
                        )
                    )
                }
            }) { Text("Save") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } }
    )
}
