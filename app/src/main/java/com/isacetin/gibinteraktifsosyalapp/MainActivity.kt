package com.isacetin.gibinteraktifsosyalapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.component.GibBottomBar
import com.isacetin.gibinteraktifsosyalapp.core.designsystem.theme.GibTheme
import com.isacetin.gibinteraktifsosyalapp.navigation.GibDestinations
import com.isacetin.gibinteraktifsosyalapp.navigation.GibNavHost
import com.isacetin.gibinteraktifsosyalapp.navigation.gibBottomBarItems
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GibTheme {
                GibApp()
            }
        }
    }
}

@Composable
fun GibApp() {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    Scaffold(
        bottomBar = {
            // Giriş ekranında alt navigasyon gizlenir.
            if (currentRoute != GibDestinations.LOGIN) {
                GibBottomBar(
                    items = gibBottomBarItems,
                    currentRoute = currentRoute,
                    onItemSelected = { item ->
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                )
            }
        },
    ) { innerPadding ->
        GibNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding),
        )
    }
}
