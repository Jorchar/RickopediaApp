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