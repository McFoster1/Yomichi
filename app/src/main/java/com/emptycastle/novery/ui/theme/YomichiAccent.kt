package com.emptycastle.novery.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp

/**
 * The current theme accent color, shared with UI pieces that use their own fixed palettes
 * (reader bar, TTS player, chapter list...). Updated by [NoveryTheme] whenever the theme changes.
 */
object YomichiAccent {
    var primary: Color by mutableStateOf(Color(0xFFFF6B35))

    val primaryLight: Color
        get() = lerp(primary, Color.White, 0.25f)
}
