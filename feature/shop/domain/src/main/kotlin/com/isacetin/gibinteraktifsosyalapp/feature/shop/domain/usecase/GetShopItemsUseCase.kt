package com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.usecase

import com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.model.ShopOverview
import com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.repository.ShopRepository
import javax.inject.Inject

/** Loads the shop catalog together with the user's current points balance. */
class GetShopItemsUseCase @Inject constructor(
    private val repository: ShopRepository,
) {
    suspend operator fun invoke(): Result<ShopOverview> {
        val items = repository.getShopItems().getOrElse { return Result.failure(it) }
        val balance = repository.getBalance().getOrElse { return Result.failure(it) }
        return Result.success(ShopOverview(items = items, balance = balance))
    }
}
