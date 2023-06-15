package com.example.rickopediaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavType
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.example.rickopediaapp.ui.screen.DetailsScreen
import com.example.rickopediaapp.ui.screen.MainScreen
import com.example.rickopediaapp.ui.theme.Test1Theme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Test1Theme {
                val navController = rememberAnimatedNavController()
                AnimatedNavHost(navController = navController, startDestination = "main") {
                    navigation(
                        startDestination = "list",
                        route = "main"
                    ) {
                        composable(
                            route = "list",
                            enterTransition = {
                                slideIntoContainer(
                                    AnimatedContentTransitionScope.SlideDirection.Right
                                )
                            },
                            exitTransition = {
                                slideOutOfContainer(
                                    AnimatedContentTransitionScope.SlideDirection.Left
                                )
                            },
                        ) {
                            MainScreen(
                                onNavigateToCharacter = { navController.navigate(it) }
                            )
                        }
                        composable(
                            route = "details/{character}",
                            enterTransition = {
                                slideIntoContainer(
                                    AnimatedContentTransitionScope.SlideDirection.Left
                                )
                            },
                            exitTransition = {
                                slideOutOfContainer(
                                    AnimatedContentTransitionScope.SlideDirection.Right
                                )
                            },
                            arguments = listOf(
                                navArgument("character") {
                                    type = NavType.IntType
                                    defaultValue = 1
                                    nullable = false
                                }
                            )
                        ) { entry ->
                            DetailsScreen(characterId = entry.arguments?.getInt("character")!!)
                        }
                    }
                }
            }
        }
    }
}