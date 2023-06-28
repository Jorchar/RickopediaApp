package com.example.rickopediaapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.ui.graphics.vector.ImageVector

enum class TopLevelDestination(
    val icon: ImageVector,
    val titleText: String
) {
    CHARACTER_LIST(
        icon = Icons.Default.Person,
        titleText = "Character list"
    ),
    EPISODE_LIST(
        icon = Icons.Default.PlayArrow,
        titleText = "Episode list"
    )
}