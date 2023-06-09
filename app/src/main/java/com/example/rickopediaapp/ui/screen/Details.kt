package com.example.rickopediaapp.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.rickopediaapp.R

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DetailsScreen(viewModel: MainViewModel, characterId: Int) {
    val character by viewModel.character.collectAsStateWithLifecycle()
    LaunchedEffect(viewModel){
        viewModel.getCharacterById(characterId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        GlideImage(
            model = character?.image,
            contentDescription = character?.name,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        ){
            it
                .centerCrop()
                .placeholder(R.drawable.image_placeholder)
        }
        Text(
            text = character!!.name,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = character!!.status
        )
        Text(
            text = character!!.gender
        )
        Text(
            text = character!!.species
        )
        Text(
            text = character!!.created
        )
    }
}