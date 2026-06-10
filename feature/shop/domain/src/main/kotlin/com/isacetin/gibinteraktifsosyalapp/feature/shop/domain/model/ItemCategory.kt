package com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.model

/** Mirrors the `shop_items.category` check constraint (`hat`/`glasses`/`outfit`/`bg`). */
enum class ItemCategory(val apiValue: String) {
    HAT("hat"),
    GLASSES("glasses"),
    OUTFIT("outfit"),
    BACKGROUND("bg"),
    ;

    companion object {
        fun fromApiValue(value: String): ItemCategory =
            entries.firstOrNull { it.apiValue == value } ?: HAT
    }
}
