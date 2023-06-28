package com.example.rickopediaapp.navigation

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.rickopediaapp.ui.screen.characters.CharacterDetailsScreen

internal const val CHARACTER_ID = "characterId"

internal class CharacterArgs(val characterId: Int) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(checkNotNull(savedStateHandle.get<Int>(CHARACTER_ID)))
}

const val characterDetailNavigationRoute = "character"

fun NavController.navigateToCharacterDetails(characterId: String) {
    val encodedId = Uri.encode(characterId)
    this.navigate("$characterDetailNavigationRoute/$encodedId") {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.characterDetailScreen(onBackClick: () -> Unit){
    composable(
        route = "$characterDetailNavigationRoute/{$CHARACTER_ID}",
        arguments = listOf(navArgument(CHARACTER_ID){ type = NavType.IntType})
    ){
        CharacterDetailsScreen(onBackClick = onBackClick)
    }
}