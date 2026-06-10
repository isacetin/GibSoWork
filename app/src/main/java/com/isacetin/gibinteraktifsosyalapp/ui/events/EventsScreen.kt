package com.isacetin.gibinteraktifsosyalapp.ui.events

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.isacetin.gibinteraktifsosyalapp.ui.common.PlaceholderScreen

@Composable
fun EventsScreen(modifier: Modifier = Modifier) {
    PlaceholderScreen(
        title = "Etkinlikler",
        description = "Şirket içi etkinlikleri burada listeleyip katılabileceksin.",
        emoji = "🎉",
        modifier = modifier,
    )
}
