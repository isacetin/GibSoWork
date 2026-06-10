package com.isacetin.gibinteraktifsosyalapp.feature.shop.presentation

import com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.model.ItemCategory
import com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.model.ShopItem

sealed interface ShopUiState {
    data object Loading : ShopUiState

    data class Content(
        val items: List<ShopItem>,
        val balance: Int,
        val selectedCategory: ItemCategory = ItemCategory.HAT,
        val purchaseConfirmation: ShopItem? = null,
        val insufficientBalanceMessage: String? = null,
    ) : ShopUiState {
        val itemsInSelectedCategory: List<ShopItem>
            get() = items.filter { it.category == selectedCategory }

        val equippedByCategory: Map<ItemCategory, ShopItem>
            get() = items.filter { it.isEquipped }.associateBy { it.category }
    }

    data class Error(val message: String) : ShopUiState
}
