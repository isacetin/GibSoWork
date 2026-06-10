package com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.usecase

import com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.model.PurchaseException
import com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.model.PurchaseOutcome
import com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.model.ShopItem
import com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.repository.ShopRepository
import javax.inject.Inject

/**
 * Purchases [item] for the current user.
 *
 * Guards (checked client-side, mirroring the `purchase_item` RPC's own rules — TC-04/TC-05):
 * - Already owned → 409-style domain error, repository is not called, balance unchanged.
 * - `balance < item.price` → 400-style domain error, repository is not called, balance unchanged.
 */
class PurchaseItemUseCase @Inject constructor(
    private val repository: ShopRepository,
) {
    suspend operator fun invoke(item: ShopItem, balance: Int): Result<PurchaseOutcome> {
        if (item.isOwned) {
            return Result.failure(PurchaseException.AlreadyOwned)
        }
        if (balance < item.price) {
            return Result.failure(PurchaseException.InsufficientBalance(shortfall = item.price - balance))
        }

        return repository.purchaseItem(item.id).map { newBalance ->
            PurchaseOutcome.Success(item = item.copy(isOwned = true), newBalance = newBalance)
        }
    }
}
