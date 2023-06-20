package com.example.rickopediaapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "episode_key")
data class EpisodeKey(
    @PrimaryKey val id: Int,
    val nextPage: Int?,
    val prevPage: Int?
)