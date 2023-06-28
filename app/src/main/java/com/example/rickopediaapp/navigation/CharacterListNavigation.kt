package com.example.rickopediaapp.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.rickopediaapp.ui.screen.characters.CharacterListScreen

const val characterListGraphRoute = "character_list_graph"
const val characterListNavigationRoute = "character_list"

fun NavController.navigateToCharacterList(navOptions: NavOptions? = null){
    this.navigate(characterListGraphRoute, navOptions)
}

fun NavGraphBuilder.characterListGraph(
    onCharacterClick: (String) -> Unit,
    nestedGraph: NavGraphBuilder.() -> Unit
){
    navigation(
        route = characterListGraphRoute,
        startDestination = characterListNavigationRoute
    ){
        composable(route = characterListNavigationRoute){
            CharacterListScreen(onCharacterClick = onCharacterClick)
        }
        nestedGraph()
    }
}