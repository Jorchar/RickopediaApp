package com.example.rickopediaapp.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.rickopediaapp.navigation.TopLevelDestination
import com.example.rickopediaapp.navigation.characterListNavigationRoute
import com.example.rickopediaapp.navigation.episodeListNavigationRoute
import com.example.rickopediaapp.navigation.navigateToCharacterList
import com.example.rickopediaapp.navigation.navigateToEpisodeList

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController()
): AppState {
    return remember(
        navController
    ) {
        AppState(
            navController
        )
    }
}

@Stable
class AppState(
    val navController: NavHostController
) {
    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
            characterListNavigationRoute -> TopLevelDestination.CHARACTER_LIST
            episodeListNavigationRoute -> TopLevelDestination.EPISODE_LIST
            else -> null
        }

    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.values().asList()

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        val topLevelNavOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
        when (topLevelDestination) {
            TopLevelDestination.CHARACTER_LIST -> navController.navigateToCharacterList(
                topLevelNavOptions
            )

            TopLevelDestination.EPISODE_LIST -> navController.navigateToEpisodeList(
                topLevelNavOptions
            )
        }
    }
}