package com.isacetin.gibinteraktifsosyalapp.core.designsystem.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * Color tokens that don't map onto the standard Material3 [androidx.compose.material3.ColorScheme]
 * slots: the gold "points" accent, success and danger states.
 */
data class GibExtendedColors(
    val accent: Color,
    val accentDeep: Color,
    val accentSoft: Color,
    val onAccent: Color,
    val success: Color,
    val successSoft: Color,
    val onSuccess: Color,
    val danger: Color,
    val dangerSoft: Color,
    val onDanger: Color,
    val surface3: Color,
)

val LightGibExtendedColors = GibExtendedColors(
    accent = LightAccent,
    accentDeep = LightAccentDeep,
    accentSoft = LightAccent.copy(alpha = 0.14f),
    onAccent = LightOnAccent,
    success = LightSuccess,
    successSoft = LightSuccess.copy(alpha = 0.12f),
    onSuccess = LightOnSuccess,
    danger = LightDanger,
    dangerSoft = LightDanger.copy(alpha = 0.10f),
    onDanger = LightOnDanger,
    surface3 = Color(0xFFEEEEF6),
)

val DarkGibExtendedColors = GibExtendedColors(
    accent = DarkAccent,
    accentDeep = DarkAccentDeep,
    accentSoft = DarkAccent.copy(alpha = 0.16f),
    onAccent = DarkOnAccent,
    success = DarkSuccess,
    successSoft = DarkSuccess.copy(alpha = 0.18f),
    onSuccess = DarkOnSuccess,
    danger = DarkDanger,
    dangerSoft = DarkDanger.copy(alpha = 0.16f),
    onDanger = DarkOnDanger,
    surface3 = Color(0xFF2B2B3A),
)

val LocalGibExtendedColors = staticCompositionLocalOf { LightGibExtendedColors }
