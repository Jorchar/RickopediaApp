package com.example.rickopediaapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.example.rickopediaapp.ui.screen.AppState

@Composable
fun AppNavHost(
    appState: AppState,
    startDestination: String = characterListGraphRoute
){
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination
    ){
        characterListGraph(
            onCharacterClick = {navController.navigateToCharacterDetails(it)},
            nestedGraph = {
                characterDetailScreen(navController::popBackStack)
            }
        )
        episodeListGraph(
            onEpisodeClick = {},
            nestedGraph = {}
        )
    }
}