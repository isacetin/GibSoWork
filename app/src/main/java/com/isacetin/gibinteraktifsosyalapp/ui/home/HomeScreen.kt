package com.isacetin.gibinteraktifsosyalapp.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.isacetin.gibinteraktifsosyalapp.ui.common.PlaceholderScreen

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    PlaceholderScreen(
        title = "Ana Sayfa",
        description = "Hero kart, hızlı eylemler ve haftalık sıralama burada görünecek.",
        emoji = "👋",
        modifier = modifier,
    )
}
