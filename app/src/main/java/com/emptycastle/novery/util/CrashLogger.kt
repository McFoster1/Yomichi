package com.emptycastle.novery.util

import android.content.Context
import android.os.Build
import java.io.File
import java.io.PrintWriter
import java.io.StringWriter
import java.util.Date

/**
 * Saves the stack trace of the last crash to a file so it can be copied from the About screen.
 */
object CrashLogger {
    private const val FILE_NAME = "last_crash.txt"

    fun install(context: Context) {
        val appContext = context.applicationContext
        val previous = Thread.getDefaultUncaughtExceptionHandler()

        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            try {
                val trace = StringWriter().also { throwable.printStackTrace(PrintWriter(it)) }.toString()
                val version = try {
                    appContext.packageManager.getPackageInfo(appContext.packageName, 0).versionName
                } catch (e: Exception) {
                    "unknown"
                }
                val report = buildString {
                    appendLine("Yomichi crash report")
                    appendLine("Time: ${Date()}")
                    appendLine("App version: $version")
                    appendLine("Android: ${Build.VERSION.RELEASE} (SDK ${Build.VERSION.SDK_INT})")
                    appendLine("Device: ${Build.MANUFACTURER} ${Build.MODEL}")
                    appendLine("Thread: ${thread.name}")
                    appendLine()
                    append(trace)
                }
                File(appContext.filesDir, FILE_NAME).writeText(report)
            } catch (e: Throwable) {
                // Never let the logger itself cause trouble
            }
            previous?.uncaughtException(thread, throwable)
        }
    }

    fun readLastCrash(context: Context): String? {
        return try {
            val file = File(context.filesDir, FILE_NAME)
            if (file.exists()) file.readText().takeIf { it.isNotBlank() } else null
        } catch (e: Exception) {
            null
        }
    }

    fun clear(context: Context) {
        try {
            File(context.filesDir, FILE_NAME).delete()
        } catch (e: Exception) {
            // ignore
        }
    }
}
