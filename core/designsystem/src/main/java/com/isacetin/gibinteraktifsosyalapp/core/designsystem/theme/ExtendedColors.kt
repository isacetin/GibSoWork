package com.isacetin.gibinteraktifsosyalapp.core.designsystem.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * Color tokens that don't map onto the standard Material3 [androidx.compose.material3.ColorScheme]
 * slots: the gold "points" accent, success and danger states.
 */
data class GibExtendedColors(
    val accent: Color,
    val onAccent: Color,
    val success: Color,
    val onSuccess: Color,
    val danger: Color,
    val onDanger: Color,
)

val LightGibExtendedColors = GibExtendedColors(
    accent = LightAccent,
    onAccent = LightOnAccent,
    success = LightSuccess,
    onSuccess = LightOnSuccess,
    danger = LightDanger,
    onDanger = LightOnDanger,
)

val DarkGibExtendedColors = GibExtendedColors(
    accent = DarkAccent,
    onAccent = DarkOnAccent,
    success = DarkSuccess,
    onSuccess = DarkOnSuccess,
    danger = DarkDanger,
    onDanger = DarkOnDanger,
)

val LocalGibExtendedColors = staticCompositionLocalOf { LightGibExtendedColors }
