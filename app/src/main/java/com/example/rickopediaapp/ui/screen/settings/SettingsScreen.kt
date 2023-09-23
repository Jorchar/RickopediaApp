package com.example.rickopediaapp.ui.screen.settings

import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import org.injinity.cointap.utils.DevicePreviews

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
){
    SettingsContent()
}

@Composable
fun SettingsContent(){
    Text(text = "Dark mode")
    Switch(checked = false, onCheckedChange = {})
}

@Composable
@DevicePreviews
fun SettingsContentPreview(){
    Surface {
        SettingsContent()
    }
}