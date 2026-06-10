package com.isacetin.gibinteraktifsosyalapp.core.designsystem.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

/** Button corner radius (16dp). Exposed as [Shapes.small]. */
val ButtonShape = RoundedCornerShape(16.dp)

/** Card corner radius (20dp). Exposed as [Shapes.medium] / [Shapes.large]. */
val CardShape = RoundedCornerShape(20.dp)

/** Fully rounded "pill" shape used for badges and chips. */
val PillShape = RoundedCornerShape(percent = 50)

/** Default avatar canvas size. */
val AvatarSize = 28.dp

val GibShapes = Shapes(
    extraSmall = PillShape,
    small = ButtonShape,
    medium = CardShape,
    large = CardShape,
    extraLarge = CardShape,
)
