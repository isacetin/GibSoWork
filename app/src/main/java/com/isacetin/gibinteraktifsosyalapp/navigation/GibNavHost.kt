package com.isacetin.gibinteraktifsosyalapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.isacetin.gibinteraktifsosyalapp.feature.events.presentation.EventsRoute
import com.isacetin.gibinteraktifsosyalapp.feature.game.presentation.GameRoute
import com.isacetin.gibinteraktifsosyalapp.feature.shop.presentation.ShopRoute
import com.isacetin.gibinteraktifsosyalapp.feature.tasks.presentation.TasksRoute
import com.isacetin.gibinteraktifsosyalapp.ui.home.HomeScreen
import com.isacetin.gibinteraktifsosyalapp.ui.profile.ProfileScreen

@Composable
fun GibNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = GibDestinations.HOME,
        modifier = modifier,
    ) {
        composable(GibDestinations.HOME) {
            HomeScreen(
                onNavigateToTasks = { navController.navigate(GibDestinations.TASKS) },
                onNavigateToShop = { navController.navigate(GibDestinations.SHOP) },
                onNavigateToGame = { navController.navigate(GibDestinations.GAME) },
            )
        }
        composable(GibDestinations.TASKS) { TasksRoute() }
        composable(GibDestinations.GAME) { GameRoute() }
        composable(GibDestinations.EVENTS) { EventsRoute() }
        composable(GibDestinations.PROFILE) { ProfileScreen() }
        composable(GibDestinations.SHOP) { ShopRoute() }
    }
}
