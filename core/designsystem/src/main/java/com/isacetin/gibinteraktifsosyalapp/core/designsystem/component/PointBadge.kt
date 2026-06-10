package com.isacetin.gibinteraktifsosyalapp.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.theme.GibExtendedTheme
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.theme.GibTheme
import java.util.Locale

/** Pill-shaped "🪙 1.240" balance indicator. Always bold + gold per the UI Kit. */
@Composable
fun PointBadge(
    points: Int,
    modifier: Modifier = Modifier,
) {
    val extendedColors = GibExtendedTheme.colors
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(percent = 50))
            .background(extendedColors.accent.copy(alpha = 0.15f))
            .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(text = "🪙")
        Text(
            text = formatPoints(points),
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = extendedColors.accent,
        )
    }
}

private fun formatPoints(points: Int): String =
    String.format(Locale.forLanguageTag("tr-TR"), "%,d", points)

@Preview
@Composable
private fun PointBadgePreview() {
    GibTheme {
        PointBadge(points = 1240)
    }
}
