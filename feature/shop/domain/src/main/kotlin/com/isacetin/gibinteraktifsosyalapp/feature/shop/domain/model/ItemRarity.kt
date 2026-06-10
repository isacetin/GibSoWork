package com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.model

/** Mirrors the `shop_items.rarity` column (`common`/`rare`/`legendary`). */
enum class ItemRarity(val apiValue: String) {
    COMMON("common"),
    RARE("rare"),
    LEGENDARY("legendary"),
    ;

    companion object {
        fun fromApiValue(value: String): ItemRarity =
            entries.firstOrNull { it.apiValue == value } ?: COMMON
    }
}
