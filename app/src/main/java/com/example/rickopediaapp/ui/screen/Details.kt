package com.example.rickopediaapp.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.rickopediaapp.R
import com.example.rickopediaapp.data.model.Character
import com.example.rickopediaapp.data.model.previewCharacter
import com.example.rickopediaapp.ui.theme.Test1Theme
import org.injinity.cointap.utils.DevicePreviews

@Composable
fun DetailsScreen(viewModel: MainViewModel = hiltViewModel(), characterId: Int) {
    val characterState by viewModel.character.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel){
        viewModel.getCharacterById(characterId)
    }

    when(characterState){
        CharacterUiState.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is CharacterUiState.Success -> {
            DetailsContent(character = (characterState as CharacterUiState.Success).character)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalFoundationApi::class)
@Composable
fun DetailsContent(
    character: Character
) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = character!!.name,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.displaySmall,
                        modifier = Modifier.weight(1f)
                    )
                    when (character.gender) {
                        "Male" -> {
                            Icon(
                                painter = painterResource(R.drawable.baseline_male),
                                contentDescription = character.gender
                            )
                        }

                        "Female" -> {
                            Icon(
                                painter = painterResource(R.drawable.baseline_female),
                                contentDescription = character.gender
                            )
                        }
                    }
                }
                Text(
                    text = character.status,
                    style = MaterialTheme.typography.titleLarge
                )
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    GlideImage(
                        model = character.image,
                        contentDescription = character.name,
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .aspectRatio(1f)
                            .padding(12.dp)
                    ) {
                        it
                            .centerCrop()
                            .placeholder(R.drawable.image_placeholder)
                            .transform(CenterCrop(), RoundedCorners(100))
                    }
                }
                Text(
                    text = "Species:",
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = character.species,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "Origin:",
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = character.origin.name,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "Episodes:",
                    style = MaterialTheme.typography.headlineMedium
                )
                val state = rememberLazyListState()
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    state = state,
                    flingBehavior = rememberSnapFlingBehavior(lazyListState = state)
                ) {
                    items(
                        count = character.episodesList.size
                    ) { index ->
                        val item = character.episodesList[index]
                        OutlinedCard(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillParentMaxWidth(0.95f)
                                .height(105.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(8.dp)
                            ) {
                                Text(
                                    text = item.episode,
                                    style = MaterialTheme.typography.titleLarge
                                )
                                Text(
                                    text = item.name,
                                    style = MaterialTheme.typography.headlineSmall,
                                )
                            }
                        }
                    }
                }
            }
        }
    }

@Composable
@DevicePreviews
fun DetailsPreview() {
    Test1Theme {
        Surface {
            DetailsContent(character = previewCharacter)
        }
    }
}