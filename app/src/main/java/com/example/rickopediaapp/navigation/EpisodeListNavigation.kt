package com.example.rickopediaapp.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.rickopediaapp.ui.screen.episodes.EpisodeListScreen

const val episodeListGraphRoute = "episode_list_graph"
const val episodeListNavigationRoute = "episode_list"

fun NavController.navigateToEpisodeList(navOptions: NavOptions? = null){
    this.navigate(episodeListGraphRoute, navOptions)
}

fun NavGraphBuilder.episodeListGraph(
    onEpisodeClick: (String) -> Unit,
    nestedGraph: NavGraphBuilder.() -> Unit
){
    navigation(
        route = episodeListGraphRoute,
        startDestination = episodeListNavigationRoute
    ){
        composable(route = episodeListNavigationRoute){
            EpisodeListScreen(onEpisodeClick = onEpisodeClick)
        }
        nestedGraph()
    }
}