package com.isacetin.gibinteraktifsosyalapp.feature.shop.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.component.GibPrimaryButton
import com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.model.ItemRarity
import com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.model.ShopItem

/** Bottom sheet asking the user to confirm spending [item.price] points. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PurchaseConfirmationSheet(
    item: ShopItem,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(),
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 8.dp)
                .padding(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            val rarityLabel = when (item.rarity) {
                ItemRarity.RARE -> "Nadir aksesuar"
                ItemRarity.LEGENDARY -> "Efsane aksesuar"
                ItemRarity.COMMON -> "Aksesuar"
            }
            Text(
                text = "${item.name} · $rarityLabel",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                text = "Bu aksesuarı al? 🪙 ${item.price}",
                style = MaterialTheme.typography.bodyLarge,
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                OutlinedButton(onClick = onDismiss, modifier = Modifier.weight(1f)) {
                    Text(text = "Vazgeç")
                }
                GibPrimaryButton(text = "Satın Al", onClick = onConfirm, modifier = Modifier.weight(1f))
            }
        }
    }
}
