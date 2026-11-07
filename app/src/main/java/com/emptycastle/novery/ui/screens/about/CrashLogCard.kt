package com.emptycastle.novery.ui.screens.about

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.emptycastle.novery.util.CrashLogger

/**
 * Shows the last crash (if any) and lets the user copy it, so it can be pasted in a bug report.
 */
@Composable
internal fun CrashLogCard() {
    val context = LocalContext.current
    var crash by remember { mutableStateOf(CrashLogger.readLastCrash(context)) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Crash log",
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = if (crash == null) {
                    "No crash has been recorded."
                } else {
                    "The app crashed last time. Copy the report and send it so the bug can be fixed."
                },
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            if (crash != null) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(onClick = {
                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        clipboard.setPrimaryClip(ClipData.newPlainText("Yomichi crash log", crash.orEmpty()))
                        Toast.makeText(context, "Crash log copied", Toast.LENGTH_SHORT).show()
                    }) {
                        Text("Copy crash log")
                    }
                    TextButton(onClick = {
                        CrashLogger.clear(context)
                        crash = null
                    }) {
                        Text("Clear")
                    }
                }
            }
        }
    }
}
