package com.isacetin.gibinteraktifsosyalapp.feature.shop.data.mapper

import com.isacetin.gibinteraktifsosyalapp.feature.shop.data.remote.dto.ShopItemDto
import com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.model.ItemCategory
import com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.model.ItemRarity
import com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.model.ShopItem

/** Maps a [ShopItemDto] to a [ShopItem], enriching it with the user's ownership/equip state. */
fun ShopItemDto.toDomain(ownedItemIds: Set<String>, equippedItemIds: Set<String>): ShopItem = ShopItem(
    id = id,
    name = name,
    category = ItemCategory.fromApiValue(category),
    rarity = ItemRarity.fromApiValue(rarity),
    price = price,
    isOwned = id in ownedItemIds,
    isEquipped = id in equippedItemIds,
)
