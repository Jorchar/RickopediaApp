package com.example.rickopediaapp.ui.screen.characters

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.rickopediaapp.R
import com.example.rickopediaapp.data.model.Character
import com.example.rickopediaapp.data.model.Episode
import com.example.rickopediaapp.data.model.previewCharacter
import com.example.rickopediaapp.data.model.previewEpisode
import com.example.rickopediaapp.ui.components.TopAppBar
import kotlinx.coroutines.flow.flowOf
import org.injinity.cointap.utils.DevicePreviews

@Composable
fun CharacterDetailsScreen(
    viewModel: CharacterDetailsViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {

    val character by viewModel.character.collectAsStateWithLifecycle()
    val episodeList = viewModel.episodes.collectAsLazyPagingItems()

    CharacterDetailsContent(
        character = character,
        episodeList = episodeList,
        onBackClick = onBackClick
    )
}

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalFoundationApi::class)
@Composable
fun CharacterDetailsContent(
    character: Character,
    episodeList: LazyPagingItems<Episode>,
    onBackClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = character.name,
            navigationIcon = Icons.Default.ArrowBack,
            navigationContentDescription = "Back to character list",
            onNavigationClick = onBackClick
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
                .scale(1f)
        ) {
            item {
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

                var color: Color = Color.White
                when (character.status) {
                    "Alive" -> {
                        color = Color.Green
                    }

                    "Dead" -> {
                        color = Color.Red
                    }

                    "unknown" -> {
                        color = Color.Yellow
                    }
                }

                Text(
                    text = "Status:",
                    style = MaterialTheme.typography.headlineMedium
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = character.status,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Icon(
                        painter = painterResource(R.drawable.baseline_circle),
                        contentDescription = character.status,
                        tint = color
                    )
                }
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
                        count = episodeList.itemCount,
                        key = episodeList.itemKey(),
                        contentType = episodeList.itemContentType()
                    ) { index ->
                        val item = episodeList[index]
                        item?.let {
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
                Text(
                    text = "Species:",
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = character.species,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "Gender:",
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = character.gender,
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
            }
        }
    }
}


@Composable
@DevicePreviews
fun CharacterDetailsPreview() {
    Surface {
        CharacterDetailsContent(
            character = previewCharacter,
            episodeList = flowOf(PagingData.from(listOf(previewEpisode))).collectAsLazyPagingItems(),
            onBackClick = {}
        )
    }
}