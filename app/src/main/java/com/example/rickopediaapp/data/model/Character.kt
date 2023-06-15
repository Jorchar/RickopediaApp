package com.example.rickopediaapp.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class Character(
    @PrimaryKey var id: Int = 0,
    @Embedded(prefix = "location_") var location: Location = Location("", ""),
    @Embedded(prefix = "origin_") var origin: Origin = Origin("", ""),
    @Ignore var episodesList: List<Episode> = emptyList(),
    var gender: String = "",
    var image: String = "",
    var name: String = "",
    var species: String = "",
    var status: String = "",
    var type: String = "",
) {
    data class Location(
        val name: String,
        val url: String
    )

    data class Origin(
        val name: String,
        val url: String
    )
}

val previewCharacter = Character(
    episodesList = listOf(),
    gender = "Male",
    id = 1,
    image = "https://example.com/character1.jpg",
    location = Character.Location(
        name = "Earth",
        url = "https://example.com/locations/earth"
    ),
    name = "John Doe",
    origin = Character.Origin(
        name = "Unknown",
        url = "https://example.com/origins/unknown"
    ),
    species = "Human",
    status = "Alive",
    type = "Main character",
)