package com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.model

/** Result of [com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.usecase.PurchaseItemUseCase]. */
sealed interface PurchaseOutcome {
    data class Success(val item: ShopItem, val newBalance: Int) : PurchaseOutcome
}
