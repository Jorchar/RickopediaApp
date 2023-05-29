package com.example.rickopediaapp.data.model

data class Character(
    val created: String,
    val episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: Location,
    val name: String,
    val origin: Origin,
    val species: String,
    val status: String,
    val type: String,
    val url: String
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
    created = "2022-03-15",
    episode = listOf("S01E01", "S01E02", "S01E03"),
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
    url = "https://example.com/characters/johndoe"
)