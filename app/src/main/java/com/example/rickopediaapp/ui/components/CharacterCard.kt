package com.example.rickopediaapp.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.rickopediaapp.R
import com.example.rickopediaapp.data.model.Character
import com.example.rickopediaapp.data.model.previewCharacter
import com.example.rickopediaapp.ui.theme.Test1Theme
import org.injinity.cointap.utils.DarkThemePreview
import org.injinity.cointap.utils.DevicePreviews

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun CharacterCard(
    character: Character,
    onCharacterClick: () -> Unit
) {
    OutlinedCard(
        modifier = Modifier
            .padding(6.dp)
            .fillMaxWidth()
            .aspectRatio(.75f),
        onClick = onCharacterClick,
    ) {
        Box {
            GlideImage(
                model = character.image,
                contentDescription = character.name,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                it
                    .centerCrop()
                    .placeholder(R.drawable.image_placeholder)
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.scrim.copy(alpha = .6f),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = character.name,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            maxLines = 1,
                        )
                        Text(
                            text = character.status,
                            color = Color.White,
                        )
                    }
                }
            }
        }
    }
}

@Composable
@DevicePreviews
@DarkThemePreview
fun MainPreview() {
    Test1Theme {
        Surface {
            CharacterCard(
                character = previewCharacter,
                onCharacterClick = {}
            )
        }
    }
}