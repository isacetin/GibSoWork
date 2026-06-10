package com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.model

sealed class PurchaseException(message: String, val code: Int) : Exception(message) {
    data class InsufficientBalance(val shortfall: Int? = null) : PurchaseException("Yetersiz bakiye", 400)
    data object AlreadyOwned : PurchaseException("Bu ürüne zaten sahipsin", 409)
}
