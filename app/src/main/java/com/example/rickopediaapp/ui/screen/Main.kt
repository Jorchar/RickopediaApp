package com.example.rickopediaapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.example.rickopediaapp.data.model.Character
import com.example.rickopediaapp.data.model.previewCharacter
import com.example.rickopediaapp.ui.CharacterCard
import com.example.rickopediaapp.ui.theme.Test1Theme
import kotlinx.coroutines.flow.flowOf
import org.injinity.cointap.utils.DarkThemePreview
import org.injinity.cointap.utils.DevicePreviews

@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel()) {
    val message by viewModel.message.collectAsStateWithLifecycle()
    val characterList = viewModel.characters.collectAsLazyPagingItems()

    MainContent(
        message = message,
        onMessageShown = viewModel::snackbarMessageShown,
        characterList = characterList,
    )
}

@Composable
fun MainContent(
    message: String?,
    onMessageShown: () -> Unit,
    characterList: LazyPagingItems<Character>,
) {
    when (characterList.loadState.refresh) {
        LoadState.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        }

        is LoadState.Error -> {
            //TODO implement error state
        }

        else -> {
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                columns = GridCells.Fixed(2)
            ) {
                items(
                    count = characterList.itemCount,
                    key = characterList.itemKey(),
                    contentType = characterList.itemContentType()
                ) { index ->
                    val item = characterList[index]
                    item?.let {
                        CharacterCard(character = item)
                    }
                }
            }
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

@Composable
@DevicePreviews
@DarkThemePreview
fun MainPreview() {
    Test1Theme {
        Surface {
            MainContent(
                message = null,
                onMessageShown = { },
                characterList = flowOf(PagingData.from(listOf(previewCharacter))).collectAsLazyPagingItems()
            )
        }
    }
}
