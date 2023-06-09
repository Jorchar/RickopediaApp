package com.example.rickopediaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.rickopediaapp.ui.screen.DetailsScreen
import com.example.rickopediaapp.ui.screen.MainScreen
import com.example.rickopediaapp.ui.screen.MainViewModel
import com.example.rickopediaapp.ui.theme.Test1Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Test1Theme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "main") {
                    navigation(
                        startDestination = "list",
                        route = "main"
                    ) {
                        composable("list") {
                            val viewModel = it.mainViewModel<MainViewModel>(navController)
                            MainScreen(viewModel, navController)
                        }
                        composable(
                            route = "details/{character}",
                            arguments = listOf(
                                navArgument("character"){
                                    type = NavType.IntType
                                    defaultValue = 1
                                    nullable = false
                                }
                            )
                        ) { entry ->
                            val viewModel = entry.mainViewModel<MainViewModel>(navController)
                            DetailsScreen(viewModel, entry.arguments?.getInt("character")!!)
                        }
                    }
                }
            }
        }
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.mainViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}