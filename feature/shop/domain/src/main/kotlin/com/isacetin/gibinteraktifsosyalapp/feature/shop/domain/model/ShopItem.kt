package com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.model

/** A single avatar accessory available in the shop. */
data class ShopItem(
    val id: String,
    val name: String,
    val category: ItemCategory,
    val rarity: ItemRarity,
    val price: Int,
    val isOwned: Boolean,
    val isEquipped: Boolean,
)
