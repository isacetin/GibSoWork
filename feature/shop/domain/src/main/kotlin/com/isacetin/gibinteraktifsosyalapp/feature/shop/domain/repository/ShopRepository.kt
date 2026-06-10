package com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.repository

import com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.model.ItemCategory
import com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.model.ShopItem

interface ShopRepository {
    /** Shop catalog enriched with the current user's ownership/equip state (`GET /shop_items`). */
    suspend fun getShopItems(): Result<List<ShopItem>>

    /** Current user's points balance (`GET /users?id=eq.{id}`). */
    suspend fun getBalance(): Result<Int>

    /** `POST /rpc/purchase_item` — returns the new balance after a successful purchase. */
    suspend fun purchaseItem(itemId: String): Result<Int>

    /** Persists [itemId] as the equipped item for [category] on the user's avatar. */
    suspend fun equipItem(itemId: String, category: ItemCategory): Result<Unit>
}
