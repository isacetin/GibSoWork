package com.isacetin.gibinteraktifsosyalapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.isacetin.gibinteraktifsosyalapp.feature.tasks.presentation.TasksRoute
import com.isacetin.gibinteraktifsosyalapp.ui.events.EventsScreen
import com.isacetin.gibinteraktifsosyalapp.ui.game.GameScreen
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
        composable(GibDestinations.HOME) { HomeScreen() }
        composable(GibDestinations.TASKS) { TasksRoute() }
        composable(GibDestinations.GAME) { GameScreen() }
        composable(GibDestinations.EVENTS) { EventsScreen() }
        composable(GibDestinations.PROFILE) { ProfileScreen() }
    }
}
