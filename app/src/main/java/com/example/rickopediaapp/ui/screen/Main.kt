package com.example.rickopediaapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.rickopediaapp.data.model.Character
import com.example.rickopediaapp.data.model.previewCharacter
import com.example.rickopediaapp.ui.theme.Test1Theme
import org.injinity.cointap.utils.DevicePreviews

@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel()) {
    val text by viewModel.text.collectAsState()
    val message by viewModel.message.collectAsState()
    val character by viewModel.character.collectAsState()
    val hello = viewModel.hello
    MainContent(
        hello = hello,
        message = message,
        onMessageShown = viewModel::snackbarMessageShown,
        character = character,
        onFetchCharacter = viewModel::fetchCharacter,
        fieldValue = text,
        onFieldValueChange = viewModel::onFieldValueChange
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(
    hello: String,
    message: String?,
    onMessageShown: () -> Unit,
    character: Character?,
    onFetchCharacter: () -> Unit,
    fieldValue: String,
    onFieldValueChange: (String) -> Unit
) {
    Box {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = hello)
            Text(text = character?.name ?: "N/A")
            TextField(
                value = fieldValue,
                onValueChange = onFieldValueChange,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
            Button(onClick = onFetchCharacter) {
                Text(text = "Fetch character")
            }
        }

        val snackbarHostState = remember { SnackbarHostState() }
        SnackbarHost(hostState = snackbarHostState, modifier = Modifier.fillMaxHeight())
        // Check for user messages to display on the screen
        message?.let { message ->
            LaunchedEffect(snackbarHostState, message) {
                snackbarHostState.showSnackbar(message)
                onMessageShown()
            }
        }
    }
}

@Composable
@DevicePreviews
fun MainPreview() {
    Test1Theme {
        Surface {
            MainContent(
                hello = "test",
                message = null,
                onMessageShown = { },
                character = previewCharacter,
                onFetchCharacter = { /*TODO*/ },
                fieldValue = "test",
                onFieldValueChange = {}
            )
        }
    }
}