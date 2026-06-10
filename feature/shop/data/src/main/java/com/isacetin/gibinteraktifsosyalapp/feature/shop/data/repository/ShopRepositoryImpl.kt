package com.isacetin.gibinteraktifsosyalapp.feature.shop.data.repository

import com.isacetin.gibinteraktifsosyalapp.core.common.ApiException
import com.isacetin.gibinteraktifsosyalapp.core.common.Constants
import com.isacetin.gibinteraktifsosyalapp.core.common.toResult
import com.isacetin.gibinteraktifsosyalapp.core.network.safeApiCall
import com.isacetin.gibinteraktifsosyalapp.feature.shop.data.mapper.toDomain
import com.isacetin.gibinteraktifsosyalapp.feature.shop.data.remote.ShopApi
import com.isacetin.gibinteraktifsosyalapp.feature.shop.data.remote.dto.PurchaseItemRequest
import com.isacetin.gibinteraktifsosyalapp.feature.shop.data.remote.dto.UpdateAvatarRequest
import com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.model.ItemCategory
import com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.model.PurchaseException
import com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.model.ShopItem
import com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.repository.ShopRepository
import javax.inject.Inject

class ShopRepositoryImpl @Inject constructor(
    private val api: ShopApi,
) : ShopRepository {

    override suspend fun getShopItems(): Result<List<ShopItem>> {
        val items = safeApiCall { api.getShopItems() }.toResult()
            .getOrElse { return Result.failure(it) }

        val ownedItemIds = safeApiCall { api.getUserItems(userId = "eq.${Constants.DEMO_USER_ID}") }.toResult()
            .getOrElse { return Result.failure(it) }
            .map { it.itemId }
            .toSet()

        val equippedItemIds = safeApiCall { api.getAvatar(userId = "eq.${Constants.DEMO_USER_ID}") }.toResult()
            .getOrElse { return Result.failure(it) }
            .firstOrNull()
            ?.equippedItems
            ?.values
            ?.toSet()
            ?: emptySet()

        return Result.success(items.map { it.toDomain(ownedItemIds, equippedItemIds) })
    }

    override suspend fun getBalance(): Result<Int> =
        safeApiCall { api.getUsers(id = "eq.${Constants.DEMO_USER_ID}") }
            .toResult()
            .map { users -> users.firstOrNull()?.pointsBalance ?: 0 }

    override suspend fun purchaseItem(itemId: String): Result<Int> =
        safeApiCall { api.purchaseItem(PurchaseItemRequest(itemId = itemId)) }
            .toResult()
            .fold(onSuccess = { getBalance() }, onFailure = { Result.failure(it.toPurchaseException()) })

    override suspend fun equipItem(itemId: String, category: ItemCategory): Result<Unit> {
        val currentEquipped = safeApiCall { api.getAvatar(userId = "eq.${Constants.DEMO_USER_ID}") }.toResult()
            .getOrElse { return Result.failure(it) }
            .firstOrNull()
            ?.equippedItems
            ?: emptyMap()

        val updatedEquipped = currentEquipped + (category.apiValue to itemId)

        return safeApiCall {
            api.updateAvatar(
                userId = "eq.${Constants.DEMO_USER_ID}",
                request = UpdateAvatarRequest(equippedItems = updatedEquipped),
            )
        }.toResult()
    }

    private fun Throwable.toPurchaseException(): Throwable =
        if (this is ApiException) {
            when (code) {
                400 -> PurchaseException.InsufficientBalance()
                409 -> PurchaseException.AlreadyOwned
                else -> this
            }
        } else {
            this
        }
}
