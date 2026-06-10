package com.isacetin.gibinteraktifsosyalapp.core.designsystem.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.theme.CardShape
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.theme.GibExtendedTheme
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.theme.GibTheme
import kotlinx.coroutines.delay
import kotlin.random.Random

/**
 * Full-screen "ödül anı" (reward moment): confetti burst + "+N 🪙" card.
 * Auto-dismisses after [autoDismissMillis].
 */
@Composable
fun RewardOverlay(
    amount: Int,
    visible: Boolean,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    autoDismissMillis: Long = 1800L,
) {
    if (visible) {
        LaunchedEffect(Unit) {
            delay(autoDismissMillis)
            onDismiss()
        }
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + scaleIn(initialScale = 0.7f),
        exit = fadeOut() + scaleOut(targetScale = 0.7f),
        modifier = modifier,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.35f)),
            contentAlignment = Alignment.Center,
        ) {
            ConfettiBurst(modifier = Modifier.fillMaxSize())

            Surface(
                shape = CardShape,
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp,
            ) {
                Column(
                    modifier = Modifier.padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(text = "🎉", fontSize = 48.sp)
                    Text(
                        text = "+$amount 🪙",
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold,
                        color = GibExtendedTheme.colors.accent,
                    )
                    Text(
                        text = "Kazandın!",
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
            }
        }
    }
}

private data class ConfettiParticle(
    val x: Float,
    val startY: Float,
    val radiusDp: Float,
    val color: Color,
)

@Composable
private fun ConfettiBurst(modifier: Modifier = Modifier) {
    val extendedColors = GibExtendedTheme.colors
    val palette = listOf(
        extendedColors.accent,
        extendedColors.success,
        MaterialTheme.colorScheme.primary,
        extendedColors.danger,
    )
    val particles = remember {
        List(28) {
            ConfettiParticle(
                x = Random.nextFloat(),
                startY = Random.nextFloat(),
                radiusDp = Random.nextInt(3, 7).toFloat(),
                color = palette[it % palette.size],
            )
        }
    }
    val transition = rememberInfiniteTransition(label = "confetti")
    val progress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1600, easing = LinearEasing),
        ),
        label = "confettiProgress",
    )
    Canvas(modifier = modifier) {
        particles.forEach { particle ->
            val y = size.height * ((particle.startY + progress) % 1f)
            drawCircle(
                color = particle.color,
                radius = particle.radiusDp.dp.toPx(),
                center = Offset(size.width * particle.x, y),
            )
        }
    }
}

@Preview
@Composable
private fun RewardOverlayPreview() {
    GibTheme {
        RewardOverlay(amount = 50, visible = true, onDismiss = {})
    }
}
