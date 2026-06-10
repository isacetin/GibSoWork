package com.isacetin.gibinteraktifsosyalapp.feature.shop.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.theme.CardShape
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.theme.GibTheme
import com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.model.ItemCategory
import com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.model.ShopItem

/**
 * Layered avatar preview: warm gradient canvas + base face overlaid with the
 * currently equipped hat/glasses/outfit emojis. Updates live as items are equipped.
 */
@Composable
fun AvatarCanvas(
    equippedByCategory: Map<ItemCategory, ShopItem>,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(220.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFFFFA45B), Color(0xFFFF7E5F)),
                    start = Offset(0f, 0f),
                    end = Offset(1000f, 1000f),
                ),
                shape = CardShape,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(text = "🙂", fontSize = 96.sp)

            equippedByCategory[ItemCategory.GLASSES]?.let {
                Text(text = ItemCategory.GLASSES.toEmoji(), fontSize = 40.sp, modifier = Modifier.padding(top = 24.dp))
            }
            equippedByCategory[ItemCategory.HAT]?.let {
                Text(
                    text = ItemCategory.HAT.toEmoji(),
                    fontSize = 56.sp,
                    modifier = Modifier.padding(bottom = 110.dp),
                )
            }
            equippedByCategory[ItemCategory.OUTFIT]?.let {
                Text(
                    text = ItemCategory.OUTFIT.toEmoji(),
                    fontSize = 56.sp,
                    modifier = Modifier.padding(top = 110.dp),
                )
            }
        }

        Text(
            text = "Avatarım",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp),
        )
    }
}

@Preview
@Composable
private fun AvatarCanvasPreview() {
    GibTheme {
        AvatarCanvas(equippedByCategory = emptyMap())
    }
}
