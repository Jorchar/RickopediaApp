package com.example.rickopediaapp.data.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "episodes")
data class Episode(
    @PrimaryKey var id: Int = 0,
    @Ignore var characters: List<String> = emptyList(),
    var air_date: String = "",
    var created: String = "",
    var episode: String = "",
    var name: String = "",
    var url: String = ""
)

val previewEpisode = Episode(
    id = 1,
    characters = listOf("Character 1", "Character 2", "Character 3"),
    air_date = "2023-06-20",
    created = "2023-06-19T12:00:00Z",
    episode = "Episode 1",
    name = "Episode Name",
    url = "https://example.com/episode1"
)