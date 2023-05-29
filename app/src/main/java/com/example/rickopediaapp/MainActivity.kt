package com.example.rickopediaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.rickopediaapp.ui.screen.MainScreen
import com.example.rickopediaapp.ui.theme.Test1Theme
import dagger.hilt.android.AndroidEntryPoint
import org.injinity.cointap.utils.DarkThemePreview
import org.injinity.cointap.utils.DevicePreviews

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Test1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

//@Preview(showBackground = true)
@DarkThemePreview
@DevicePreviews
@Composable
fun GreetingPreview() {
    Test1Theme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Greeting("Android")
        }
    }
}