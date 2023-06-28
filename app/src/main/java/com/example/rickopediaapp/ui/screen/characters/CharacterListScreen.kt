package com.example.rickopediaapp.ui.screen.characters

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
import com.example.rickopediaapp.ui.components.CharacterCard
import kotlinx.coroutines.flow.flowOf
import org.injinity.cointap.utils.DarkThemePreview
import org.injinity.cointap.utils.DevicePreviews

@Composable
fun CharacterListScreen(
    viewModel: CharacterListViewModel = hiltViewModel(),
    onCharacterClick: (String) -> Unit
) {
    val message by viewModel.message.collectAsStateWithLifecycle()
    val characterList = viewModel.characters.collectAsLazyPagingItems()

    CharacterListContent(
        message = message,
        onMessageShown = viewModel::snackbarMessageShown,
        characterList = characterList,
        onCharacterClick = onCharacterClick
    )
}

@Composable
fun CharacterListContent(
    message: String?,
    onMessageShown: () -> Unit,
    characterList: LazyPagingItems<Character>,
    onCharacterClick: (String) -> Unit
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
                columns = GridCells.Fixed(2)
            ) {
                items(
                    count = characterList.itemCount,
                    key = characterList.itemKey(),
                    contentType = characterList.itemContentType()
                ) { index ->
                    val item = characterList[index]
                    item?.let {
                        CharacterCard(
                            character = item,
                            onNavigateToCharacter = onCharacterClick
                        )
                    }
                }
            }
        }
    }

    val snackbarHostState = remember { SnackbarHostState() }
    SnackbarHost(hostState = snackbarHostState, modifier = Modifier.fillMaxHeight())
    // Check for user messages to display on the screen
    message?.let { msg ->
        LaunchedEffect(snackbarHostState, msg) {
            snackbarHostState.showSnackbar(msg)
            onMessageShown()
        }
    }
}

@Composable
@DevicePreviews
@DarkThemePreview
fun CharacterListPreview() {
    Surface {
        CharacterListContent(
            message = null,
            onMessageShown = { },
            characterList = flowOf(PagingData.from(listOf(previewCharacter))).collectAsLazyPagingItems(),
            onCharacterClick = fun(_) {}
        )
    }
}
