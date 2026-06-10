package com.isacetin.gibinteraktifsosyalapp.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.component.EmptyState

/** Temporary placeholder for screens not yet implemented (Faz 2+). */
@Composable
fun PlaceholderScreen(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    emoji: String = "🚧",
) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        EmptyState(title = title, description = description, emoji = emoji)
    }
}
