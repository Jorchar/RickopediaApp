package com.example.rickopediaapp.data.model

data class Character(
    val episodesList: List<Episode>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: Location,
    val name: String,
    val origin: Origin,
    val species: String,
    val status: String,
    val type: String,
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