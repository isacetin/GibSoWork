package com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.model

/** Combined result of [com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.usecase.GetShopItemsUseCase]. */
data class ShopOverview(
    val items: List<ShopItem>,
    val balance: Int,
)
