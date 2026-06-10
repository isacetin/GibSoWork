package com.isacetin.gibinteraktifsosyalapp.feature.shop.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.theme.CardShape
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.theme.GibExtendedTheme
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.theme.GibTheme
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.theme.PillShape
import com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.model.ItemCategory
import com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.model.ItemRarity
import com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.model.ShopItem

/** A single shop item: rarity strip, emoji, name and ownership/equip/price status. */
@Composable
fun ShopItemCard(
    item: ShopItem,
    onClick: (ShopItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(item) },
        shape = CardShape,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            RarityBadge(rarity = item.rarity, modifier = Modifier.align(Alignment.Start))

            Text(text = item.category.toEmoji(), fontSize = 40.sp)

            Text(
                text = item.name,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            )

            ItemStatusBadge(item = item)
        }
    }
}

@Composable
private fun RarityBadge(rarity: ItemRarity, modifier: Modifier = Modifier) {
    // Always rendered (invisible for COMMON) so every card reserves the same space.
    val label = when (rarity) {
        ItemRarity.RARE -> "NADIR"
        ItemRarity.LEGENDARY -> "EFSANE"
        ItemRarity.COMMON -> "NADIR"
    }
    val color = when (rarity) {
        ItemRarity.LEGENDARY -> GibExtendedTheme.colors.accent
        else -> MaterialTheme.colorScheme.primary
    }
    Text(
        text = label,
        style = MaterialTheme.typography.labelSmall,
        fontWeight = FontWeight.Bold,
        color = color,
        modifier = modifier
            .background(color = color.copy(alpha = 0.12f), shape = RoundedCornerShape(percent = 50))
            .padding(horizontal = 8.dp, vertical = 2.dp)
            .alpha(if (rarity == ItemRarity.COMMON) 0f else 1f),
    )
}

@Composable
private fun ItemStatusBadge(item: ShopItem) {
    val (label, color) = when {
        item.isEquipped -> "Giyili ✓" to GibExtendedTheme.colors.success
        item.isOwned -> "Sahip" to MaterialTheme.colorScheme.onSurfaceVariant
        else -> "🪙 ${item.price}" to GibExtendedTheme.colors.accent
    }
    Text(
        text = label,
        style = MaterialTheme.typography.labelMedium,
        fontWeight = FontWeight.Bold,
        color = color,
        modifier = Modifier
            .background(color = color.copy(alpha = 0.12f), shape = PillShape)
            .padding(horizontal = 10.dp, vertical = 4.dp),
    )
}

@Preview
@Composable
private fun ShopItemCardPreview() {
    GibTheme {
        ShopItemCard(
            item = ShopItem(
                id = "1",
                name = "Kovboy Şapkası",
                category = ItemCategory.HAT,
                rarity = ItemRarity.RARE,
                price = 120,
                isOwned = false,
                isEquipped = false,
            ),
            onClick = {},
        )
    }
}
