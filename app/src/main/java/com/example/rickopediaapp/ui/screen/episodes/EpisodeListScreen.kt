package com.example.rickopediaapp.ui.screen.episodes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.injinity.cointap.utils.DevicePreviews

@Composable
fun EpisodeListScreen(onEpisodeClick: (String) -> Unit) {
    EpisodeListContent(onEpisodeClick = onEpisodeClick)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EpisodeListContent(
    onEpisodeClick: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            onClick = { onEpisodeClick("1") }
        ) {
            Text(
                text = "Hello", fontSize = MaterialTheme.typography.displayLarge.fontSize
            )
        }
    }
}

@Composable
@DevicePreviews
fun EpisodeListPreview() {
    Surface {
        EpisodeListContent(onEpisodeClick = {})
    }
}