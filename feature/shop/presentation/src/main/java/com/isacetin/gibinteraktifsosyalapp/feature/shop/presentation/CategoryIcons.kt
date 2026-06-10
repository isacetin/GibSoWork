package com.isacetin.gibinteraktifsosyalapp.feature.shop.presentation

import com.isacetin.gibinteraktifsosyalapp.feature.shop.domain.model.ItemCategory

/** Emoji glyph used as a stand-in for the item's `asset_key` artwork. */
fun ItemCategory.toEmoji(): String = when (this) {
    ItemCategory.HAT -> "🎩"
    ItemCategory.GLASSES -> "🕶️"
    ItemCategory.OUTFIT -> "👔"
    ItemCategory.BACKGROUND -> "🌄"
}

/** Tab label shown in the category pills. */
fun ItemCategory.toLabel(): String = when (this) {
    ItemCategory.HAT -> "Şapka"
    ItemCategory.GLASSES -> "Gözlük"
    ItemCategory.OUTFIT -> "Kıyafet"
    ItemCategory.BACKGROUND -> "Arka Plan"
}
