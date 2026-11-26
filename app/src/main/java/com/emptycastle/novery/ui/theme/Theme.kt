package com.emptycastle.novery.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.emptycastle.novery.domain.model.AppSettings
import com.emptycastle.novery.domain.model.CustomThemeColors
import com.emptycastle.novery.domain.model.ThemeMode

/**
 * Dark color scheme - Yomichi default (Midnight + Soft Cyan)
 */
private val DarkColorScheme = darkColorScheme(
    primary = YomichiCyan,
    onPrimary = YomichiMidnight,
    primaryContainer = Color(0xFF16414B),
    onPrimaryContainer = Color(0xFFCFF5FB),
    secondary = Color(0xFF4FB3C7),
    onSecondary = YomichiMidnight,
    secondaryContainer = Color(0xFF1B2430),
    onSecondaryContainer = Color(0xFFE2E8F0),
    tertiary = Color(0xFF9DB4CA),
    onTertiary = YomichiMidnight,
    tertiaryContainer = Color(0xFF222B38),
    onTertiaryContainer = Color(0xFFE2E8F0),
    background = YomichiMidnight,
    onBackground = Color(0xFFF1F5F9),
    surface = YomichiMidnight,
    onSurface = Color(0xFFF1F5F9),
    surfaceVariant = YomichiSurface,
    onSurfaceVariant = YomichiMuted,
    surfaceContainerLowest = YomichiMidnight,
    surfaceContainerLow = YomichiSurface,
    surfaceContainer = Color(0xFF1B2330),
    surfaceContainerHigh = Color(0xFF222B38),
    surfaceContainerHighest = Color(0xFF2A3544),
    inverseSurface = Color(0xFFF1F5F9),
    inverseOnSurface = YomichiSurface,
    inversePrimary = YomichiCyanDeep,
    error = Error,
    onError = Color.White,
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),
    outline = Color(0xFF2E3A49),
    outlineVariant = YomichiBorder,
    scrim = Color.Black
)

/**
 * AMOLED Black color scheme
 */
private val AmoledDarkColorScheme = DarkColorScheme.copy(
    background = Color.Black,
    surface = Color.Black,
    surfaceContainerLowest = Color.Black,
    surfaceContainerLow = Color(0xFF0A0A0A),
    surfaceContainer = Color(0xFF121212),
    surfaceContainerHigh = Color(0xFF1A1A1A),
    surfaceContainerHighest = Color(0xFF222222)
)

/**
 * Light color scheme
 */
private val LightColorScheme = lightColorScheme(
    primary = YomichiCyanDeep,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFCFF5FB),
    onPrimaryContainer = Color(0xFF083344),
    secondary = Color(0xFF0891B2),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFE0F7FB),
    onSecondaryContainer = Color(0xFF083344),
    background = Color(0xFFF7FAFC),
    onBackground = Zinc900,
    surface = Color.White,
    onSurface = Zinc900,
    surfaceVariant = Color(0xFFEEF3F7),
    onSurfaceVariant = Color(0xFF475569),
    error = Error,
    onError = Color.White,
    outline = Color(0xFFCBD5E1),
    outlineVariant = Color(0xFFE2E8F0)
)

/**
 * Creates a custom dark color scheme based on user-selected colors
 */
private fun createCustomDarkColorScheme(colors: CustomThemeColors): ColorScheme {
    val primary = Color(colors.primaryColor)
    val secondary = Color(colors.secondaryColor)
    val background = Color(colors.backgroundColor)
    val surface = Color(colors.surfaceColor)

    // Generate derived colors
    val onPrimary = getContrastColor(primary)
    val onSecondary = getContrastColor(secondary)
    val onBackground = getContrastColor(background)
    val onSurface = getContrastColor(surface)

    // Create lighter/darker variants
    val primaryContainer = primary.copy(alpha = 0.3f).compositeOver(background)
    val secondaryContainer = secondary.copy(alpha = 0.2f).compositeOver(surface)
    val surfaceVariant = blendColors(surface, Color.White, 0.05f)
    val surfaceContainer = blendColors(surface, Color.White, 0.08f)
    val surfaceContainerHigh = blendColors(surface, Color.White, 0.12f)
    val surfaceContainerHighest = blendColors(surface, Color.White, 0.16f)
    val surfaceContainerLow = blendColors(surface, background, 0.5f)

    return darkColorScheme(
        primary = primary,
        onPrimary = onPrimary,
        primaryContainer = primaryContainer,
        onPrimaryContainer = primary,
        secondary = secondary,
        onSecondary = onSecondary,
        secondaryContainer = secondaryContainer,
        onSecondaryContainer = secondary,
        tertiary = secondary.copy(alpha = 0.8f),
        onTertiary = onSecondary,
        tertiaryContainer = secondaryContainer,
        onTertiaryContainer = secondary,
        background = background,
        onBackground = onBackground,
        surface = surface,
        onSurface = onSurface,
        surfaceVariant = surfaceVariant,
        onSurfaceVariant = onSurface.copy(alpha = 0.7f),
        surfaceContainerLowest = background,
        surfaceContainerLow = surfaceContainerLow,
        surfaceContainer = surfaceContainer,
        surfaceContainerHigh = surfaceContainerHigh,
        surfaceContainerHighest = surfaceContainerHighest,
        inverseSurface = onSurface,
        inverseOnSurface = surface,
        inversePrimary = primary.copy(alpha = 0.8f),
        error = Error,
        onError = Color.White,
        errorContainer = Color(0xFF93000A),
        onErrorContainer = Color(0xFFFFDAD6),
        outline = surfaceContainerHigh,
        outlineVariant = surfaceContainer,
        scrim = Color.Black
    )
}

/**
 * Creates a custom light color scheme based on user-selected colors
 */
private fun createCustomLightColorScheme(colors: CustomThemeColors): ColorScheme {
    val primary = Color(colors.primaryColor)
    val secondary = Color(colors.secondaryColor)

    // For light mode, we use lighter backgrounds
    val background = Color(0xFFFAFAFA)
    val surface = Color.White

    val onPrimary = getContrastColor(primary)
    val onSecondary = getContrastColor(secondary)

    val primaryContainer = primary.copy(alpha = 0.12f).compositeOver(surface)
    val secondaryContainer = secondary.copy(alpha = 0.12f).compositeOver(surface)

    return lightColorScheme(
        primary = primary,
        onPrimary = onPrimary,
        primaryContainer = primaryContainer,
        onPrimaryContainer = primary.darken(0.3f),
        secondary = secondary,
        onSecondary = onSecondary,
        secondaryContainer = secondaryContainer,
        onSecondaryContainer = secondary.darken(0.3f),
        background = background,
        onBackground = Zinc900,
        surface = surface,
        onSurface = Zinc900,
        surfaceVariant = Zinc100,
        onSurfaceVariant = Zinc700,
        error = Error,
        onError = Color.White,
        outline = Zinc300,
        outlineVariant = Zinc200
    )
}

/**
 * Utility function to get contrasting color (black or white)
 */
private fun getContrastColor(color: Color): Color {
    val luminance = 0.299 * color.red + 0.587 * color.green + 0.114 * color.blue
    return if (luminance > 0.5) Color.Black else Color.White
}

/**
 * Blend two colors together
 */
private fun blendColors(color1: Color, color2: Color, ratio: Float): Color {
    val inverseRatio = 1f - ratio
    return Color(
        red = (color1.red * inverseRatio + color2.red * ratio).coerceIn(0f, 1f),
        green = (color1.green * inverseRatio + color2.green * ratio).coerceIn(0f, 1f),
        blue = (color1.blue * inverseRatio + color2.blue * ratio).coerceIn(0f, 1f),
        alpha = 1f
    )
}

/**
 * Darken a color by a given factor
 */
private fun Color.darken(factor: Float): Color {
    return Color(
        red = (red * (1 - factor)).coerceIn(0f, 1f),
        green = (green * (1 - factor)).coerceIn(0f, 1f),
        blue = (blue * (1 - factor)).coerceIn(0f, 1f),
        alpha = alpha
    )
}

/**
 * Composite one color over another
 */
private fun Color.compositeOver(background: Color): Color {
    val fgAlpha = this.alpha
    val bgAlpha = background.alpha
    val outAlpha = fgAlpha + bgAlpha * (1f - fgAlpha)

    return if (outAlpha == 0f) {
        Color.Transparent
    } else {
        Color(
            red = (red * fgAlpha + background.red * bgAlpha * (1f - fgAlpha)) / outAlpha,
            green = (green * fgAlpha + background.green * bgAlpha * (1f - fgAlpha)) / outAlpha,
            blue = (blue * fgAlpha + background.blue * bgAlpha * (1f - fgAlpha)) / outAlpha,
            alpha = outAlpha
        )
    }
}

/**
 * Main theme composable for Novery with settings support
 */
@Composable
fun NoveryTheme(
    appSettings: AppSettings = AppSettings(),
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val view = LocalView.current

    // Determine if dark theme based on settings
    val useDarkTheme = when (appSettings.themeMode) {
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
    }

    // Select color scheme
    val colorScheme = when {
        // Custom theme takes priority (but not over dynamic color if that's enabled)
        appSettings.useCustomTheme && !appSettings.useDynamicColor -> {
            if (useDarkTheme) {
                val customScheme = createCustomDarkColorScheme(appSettings.customThemeColors)
                if (appSettings.amoledBlack) {
                    customScheme.copy(
                        background = Color.Black,
                        surface = Color.Black,
                        surfaceContainerLowest = Color.Black,
                        surfaceContainerLow = Color(0xFF0A0A0A),
                        surfaceContainer = Color(0xFF121212),
                        surfaceContainerHigh = Color(0xFF1A1A1A),
                        surfaceContainerHighest = Color(0xFF222222)
                    )
                } else {
                    customScheme
                }
            } else {
                createCustomLightColorScheme(appSettings.customThemeColors)
            }
        }
        // Dynamic color (Material You)
        appSettings.useDynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (useDarkTheme) {
                dynamicDarkColorScheme(context)
            } else {
                dynamicLightColorScheme(context)
            }
        }
        // AMOLED black (only in dark mode)
        useDarkTheme && appSettings.amoledBlack -> AmoledDarkColorScheme
        // Standard dark
        useDarkTheme -> DarkColorScheme
        // Light
        else -> LightColorScheme
    }

    // Update system bars
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            val backgroundColor = when {
                appSettings.useCustomTheme && !appSettings.useDynamicColor -> {
                    if (useDarkTheme) {
                        if (appSettings.amoledBlack) Color.Black
                        else Color(appSettings.customThemeColors.backgroundColor)
                    } else {
                        Zinc50
                    }
                }
                useDarkTheme -> {
                    if (appSettings.amoledBlack) Color.Black else Zinc950
                }
                else -> Zinc50
            }

            window.statusBarColor = backgroundColor.toArgb()
            window.navigationBarColor = backgroundColor.toArgb()

            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = !useDarkTheme
                isAppearanceLightNavigationBars = !useDarkTheme
            }
        }
    }

    // Keep the shared accent color in sync with the active theme
    SideEffect { YomichiAccent.primary = colorScheme.primary }

    // Provide dimensions based on density
    ProvideDimensions(density = appSettings.uiDensity) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = NoveryTypography,
            content = content
        )
    }
}

/**
 * Legacy theme for backward compatibility (always dark)
 */
@Composable
fun NoveryTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    NoveryTheme(
        appSettings = AppSettings(
            themeMode = if (darkTheme) ThemeMode.DARK else ThemeMode.LIGHT
        ),
        content = content
    )
}