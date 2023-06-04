package com.example.rickopediaapp.ui
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.rickopediaapp.data.model.Character
import com.example.rickopediaapp.data.model.previewCharacter
import com.example.rickopediaapp.ui.theme.Test1Theme
import org.injinity.cointap.utils.DarkThemePreview
import org.injinity.cointap.utils.DevicePreviews

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun CharacterCard(character: Character) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .wrapContentHeight(),
        shape = CardDefaults.elevatedShape,
        colors = CardDefaults.elevatedCardColors(),
        elevation = CardDefaults.elevatedCardElevation(),
        onClick = { TODO("jump to character specific information event")}
    ) {
        GlideImage(
            model = character.image,
            contentDescription = character.name,
            modifier = Modifier.fillMaxSize()
        ){
            it
                .centerCrop()
                .apply(RequestOptions().transform(RoundedCorners(30)))

        }
        Text(
            text = character.name,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(4.dp),
            maxLines = 1
        )
        Text(
            text = character.status,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
@DevicePreviews
@DarkThemePreview
fun MainPreview() {
    Test1Theme {
        Surface {
            CharacterCard(
                character = previewCharacter
            )
        }
    }
}