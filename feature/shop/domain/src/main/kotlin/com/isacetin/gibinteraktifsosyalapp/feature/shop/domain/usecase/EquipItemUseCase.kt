package com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.usecase

import com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.model.ShopItem
import com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.repository.ShopRepository
import javax.inject.Inject

/**
 * Equips [item] on the user's avatar.
 *
 * Guards: only owned items can be equipped; equipping an already-equipped item is a no-op.
 */
class EquipItemUseCase @Inject constructor(
    private val repository: ShopRepository,
) {
    suspend operator fun invoke(item: ShopItem): Result<Unit> {
        if (!item.isOwned) {
            return Result.failure(IllegalStateException("Item '${item.id}' must be owned before it can be equipped"))
        }
        if (item.isEquipped) {
            return Result.success(Unit)
        }
        return repository.equipItem(item.id, item.category)
    }
}
