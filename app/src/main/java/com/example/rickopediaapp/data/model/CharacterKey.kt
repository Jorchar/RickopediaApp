package com.example.rickopediaapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character_key")
data class CharacterKey(
    @PrimaryKey val id: Int,
    val nextPage: Int?,
    val prevPage: Int?
)
