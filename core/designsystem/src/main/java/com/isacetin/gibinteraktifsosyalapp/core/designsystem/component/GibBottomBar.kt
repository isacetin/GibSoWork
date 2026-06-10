package com.isacetin.gibinteraktifsosyalapp.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.theme.PillShape

/** A single destination shown in [GibBottomBar]. */
data class GibBottomBarItem(
    val route: String,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)

/** UI Kit `BottomNav` variant B: floating pill bar with a raised center "Oyna" FAB. */
@Composable
fun GibBottomBar(
    items: List<GibBottomBarItem>,
    currentRoute: String?,
    onItemSelected: (GibBottomBarItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    val colorScheme = MaterialTheme.colorScheme

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(108.dp)
            .padding(start = 16.dp, end = 16.dp, bottom = 22.dp),
        contentAlignment = Alignment.BottomCenter,
    ) {
        val playItem = items.firstOrNull { it.label == "Oyna" }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .shadow(
                    elevation = 18.dp,
                    shape = PillShape,
                    clip = false,
                )
                .background(colorScheme.surface.copy(alpha = 0.92f), PillShape)
                .border(1.dp, colorScheme.outline, PillShape)
                .padding(horizontal = 14.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            items.forEach { item ->
                val selected = currentRoute == item.route
                if (item.label == "Oyna") {
                    PlayLabelSlot(
                        item = item,
                        selected = selected,
                        onClick = { onItemSelected(item) },
                    )
                } else {
                    FloatingNavItem(
                        item = item,
                        selected = selected,
                        onClick = { onItemSelected(item) },
                    )
                }
            }
        }
        playItem?.let { item ->
            PlayFab(
                item = item,
                onClick = { onItemSelected(item) },
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = 0.dp),
            )
        }
    }
}

@Composable
private fun FloatingNavItem(
    item: GibBottomBarItem,
    selected: Boolean,
    onClick: () -> Unit,
) {
    val contentColor = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant

    Column(
        modifier = Modifier
            .width(58.dp)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(3.dp),
    ) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .background(
                    color = if (selected) MaterialTheme.colorScheme.primary.copy(alpha = 0.12f) else Color.Transparent,
                    shape = PillShape,
                ),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                contentDescription = item.label,
                tint = contentColor,
                modifier = Modifier.size(23.dp),
            )
        }
        Text(
            text = item.label,
            color = contentColor,
            fontSize = 10.5.sp,
            lineHeight = 12.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
            maxLines = 1,
        )
    }
}

@Composable
private fun PlayLabelSlot(
    item: GibBottomBarItem,
    selected: Boolean,
    onClick: () -> Unit,
) {
    val colorScheme = MaterialTheme.colorScheme
    val labelColor = if (selected) colorScheme.primary else colorScheme.onSurfaceVariant

    Box(
        modifier = Modifier
            .width(60.dp)
            .height(64.dp)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Text(
            text = item.label,
            modifier = Modifier.padding(bottom = 8.dp),
            color = labelColor,
            fontSize = 11.sp,
            lineHeight = 12.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
        )
    }
}

@Composable
private fun PlayFab(
    item: GibBottomBarItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colorScheme = MaterialTheme.colorScheme

    Box(
        modifier = modifier
            .size(58.dp)
            .shadow(
                elevation = 14.dp,
                shape = PillShape,
                clip = false,
            )
            .background(
                brush = Brush.linearGradient(listOf(colorScheme.primary, Color(0xFF4F4FC9))),
                shape = PillShape,
            )
            .border(3.dp, colorScheme.surface, PillShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = item.selectedIcon,
            contentDescription = item.label,
            tint = colorScheme.onPrimary,
            modifier = Modifier.size(26.dp),
        )
    }
}
