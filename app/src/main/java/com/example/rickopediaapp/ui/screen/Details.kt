package com.example.rickopediaapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.rickopediaapp.R

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DetailsScreen(viewModel: MainViewModel, characterId: Int) {
    val character by viewModel.character.collectAsStateWithLifecycle()
    LaunchedEffect(viewModel) {
        viewModel.getCharacterById(characterId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {

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
            when(character!!.gender){
                "Male"-> {
                    Icon(
                        painter = painterResource(R.drawable.baseline_male),
                        contentDescription = character!!.gender
                    )
                }
                "Female"-> {
                    Icon(
                        painter = painterResource(R.drawable.baseline_female),
                        contentDescription = character!!.gender
                    )
                }
            }
        }
        Text(
            text = character!!.status,
            style = MaterialTheme.typography.titleLarge
        )
        GlideImage(
            model = character?.image,
            contentDescription = character?.name,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .padding(12.dp)
        ) {
            it
                .centerCrop()
                .placeholder(R.drawable.image_placeholder)
                .transform(CenterCrop(), RoundedCorners(100))
        }
        Text(
            text = "Species:",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = character!!.species,
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = "Origin:",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = character!!.origin.name,
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = "Episodes:",
            style = MaterialTheme.typography.headlineMedium
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
        ){
            items(
                count = character!!.episodesList.size
            ){index ->
                val item = character!!.episodesList[index]
                Text(
                    text = item.episode,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
    }
}