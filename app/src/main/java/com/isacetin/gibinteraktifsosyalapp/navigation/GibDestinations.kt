package com.isacetin.gibinteraktifsosyalapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.Task
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.SportsEsports
import androidx.compose.material.icons.outlined.Task
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.component.GibBottomBarItem

/** Route names for the 5 bottom-navigation tabs. */
object GibDestinations {
    const val HOME = "home"
    const val TASKS = "tasks"
    const val GAME = "game"
    const val EVENTS = "events"
    const val PROFILE = "profile"

    /** Not a bottom-nav tab — opened from the Home "Shop" quick action (§2 Avatar & Shop). */
    const val SHOP = "shop"
}

val gibBottomBarItems = listOf(
    GibBottomBarItem(
        route = GibDestinations.HOME,
        label = "Ana Sayfa",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
    ),
    GibBottomBarItem(
        route = GibDestinations.TASKS,
        label = "Görevler",
        selectedIcon = Icons.Filled.Task,
        unselectedIcon = Icons.Outlined.Task,
    ),
    GibBottomBarItem(
        route = GibDestinations.GAME,
        label = "Oyna",
        selectedIcon = Icons.Filled.SportsEsports,
        unselectedIcon = Icons.Outlined.SportsEsports,
    ),
    GibBottomBarItem(
        route = GibDestinations.EVENTS,
        label = "Etkinlik",
        selectedIcon = Icons.Filled.EmojiEvents,
        unselectedIcon = Icons.Outlined.EmojiEvents,
    ),
    GibBottomBarItem(
        route = GibDestinations.PROFILE,
        label = "Profil",
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person,
    ),
)
