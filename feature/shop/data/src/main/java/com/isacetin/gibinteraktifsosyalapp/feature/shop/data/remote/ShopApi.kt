package com.isacetin.gibinteraktifsosyalapp.feature.shop.data.remote

import com.isacetin.gibinteraktifsosyalapp.feature.shop.data.remote.dto.AvatarDto
import com.isacetin.gibinteraktifsosyalapp.feature.shop.data.remote.dto.PurchaseItemRequest
import com.isacetin.gibinteraktifsosyalapp.feature.shop.data.remote.dto.ShopItemDto
import com.isacetin.gibinteraktifsosyalapp.feature.shop.data.remote.dto.UpdateAvatarRequest
import com.isacetin.gibinteraktifsosyalapp.feature.shop.data.remote.dto.UserBalanceDto
import com.isacetin.gibinteraktifsosyalapp.feature.shop.data.remote.dto.UserItemDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

/** Supabase PostgREST endpoints used by `:feature:shop` (see docs/02_API_TEST.md, docs/03_MIMARI_PLAN.md). */
interface ShopApi {

    @GET("shop_items")
    suspend fun getShopItems(): List<ShopItemDto>

    @GET("user_items")
    suspend fun getUserItems(
        @Query("user_id") userId: String,
        @Query("select") select: String = "item_id",
    ): List<UserItemDto>

    @GET("avatars")
    suspend fun getAvatar(@Query("user_id") userId: String): List<AvatarDto>

    @GET("users")
    suspend fun getUsers(@Query("id") id: String): List<UserBalanceDto>

    @Headers("Prefer: return=minimal")
    @POST("rpc/purchase_item")
    suspend fun purchaseItem(@Body request: PurchaseItemRequest)

    @Headers("Prefer: return=minimal")
    @PATCH("avatars")
    suspend fun updateAvatar(@Query("user_id") userId: String, @Body request: UpdateAvatarRequest)
}
