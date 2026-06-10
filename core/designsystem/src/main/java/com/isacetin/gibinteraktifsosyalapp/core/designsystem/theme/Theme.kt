package com.isacetin.gibinteraktifsosyalapp.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

private val LightColorScheme = lightColorScheme(
    primary = LightPrimary,
    onPrimary = LightOnPrimary,
    primaryContainer = LightPrimaryContainer,
    onPrimaryContainer = LightOnPrimaryContainer,
    background = LightBackground,
    onBackground = LightOnBackground,
    surface = LightSurface,
    onSurface = LightOnSurface,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = LightOnSurfaceVariant,
    outline = LightOutline,
    error = LightDanger,
    onError = LightOnDanger,
)

private val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    onPrimary = DarkOnPrimary,
    primaryContainer = DarkPrimaryContainer,
    onPrimaryContainer = DarkOnPrimaryContainer,
    background = DarkBackground,
    onBackground = DarkOnBackground,
    surface = DarkSurface,
    onSurface = DarkOnSurface,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkOnSurfaceVariant,
    outline = DarkOutline,
    error = DarkDanger,
    onError = DarkOnDanger,
)

/** App-wide theme. Wrap the whole app (and previews) with this. */
@Composable
fun GibTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val extendedColors = if (darkTheme) DarkGibExtendedColors else LightGibExtendedColors

    CompositionLocalProvider(LocalGibExtendedColors provides extendedColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = GibTypography,
            shapes = GibShapes,
            content = content,
        )
    }
}

/** Access point for [GibExtendedColors], mirroring `MaterialTheme.colorScheme`. */
object GibExtendedTheme {
    val colors: GibExtendedColors
        @Composable
        get() = LocalGibExtendedColors.current
}
