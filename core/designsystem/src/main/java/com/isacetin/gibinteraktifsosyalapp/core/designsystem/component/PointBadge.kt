package com.isacetin.gibinteraktifsosyalapp.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.theme.GibExtendedTheme
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.theme.GibTheme
import java.util.Locale

/** UI Kit `PointBadge`: soft gold pill, bordered, with radial-gradient coin. */
@Composable
fun PointBadge(
    points: Int,
    modifier: Modifier = Modifier,
    big: Boolean = false,
) {
    val colors = GibExtendedTheme.colors
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(percent = 50))
            .background(colors.accentSoft)
            .border(
                width = 1.dp,
                color = colors.accent.copy(alpha = 0.30f),
                shape = RoundedCornerShape(percent = 50),
            )
            .padding(horizontal = if (big) 16.dp else 12.dp, vertical = if (big) 8.dp else 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(if (big) 8.dp else 6.dp),
    ) {
        CoinIcon(size = if (big) 22.dp else 17.dp)
        Text(
            text = formatPoints(points),
            style = if (big) MaterialTheme.typography.titleLarge else MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.ExtraBold,
            color = colors.accentDeep,
        )
    }
}

/** UI Kit `Coin`: radial gold disk with the small ₺ glyph. */
@Composable
fun CoinIcon(
    modifier: Modifier = Modifier,
    size: Dp = 17.dp,
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(percent = 50))
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        Color(0xFFFFE08A),
                        Color(0xFFFFC24D),
                        Color(0xFFE59A1C),
                    ),
                ),
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "₺",
            color = Color(0xFF8A5A0E),
            fontSize = (size.value * 0.56f).sp,
            fontWeight = FontWeight.ExtraBold,
            lineHeight = (size.value * 0.56f).sp,
        )
    }
}

private fun formatPoints(points: Int): String =
    String.format(Locale.forLanguageTag("tr-TR"), "%,d", points)

@Preview
@Composable
private fun PointBadgePreview() {
    GibTheme {
        PointBadge(points = 1240, big = true)
    }
}
