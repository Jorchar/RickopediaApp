package com.example.rickopediaapp.data.model

import com.example.rickopediaapp.data.remote.GetCharacterResponse

object CharacterMapper {

    fun buildFrom(
        response: GetCharacterResponse,
    ): Character {
        return Character(
            episodesList = response.episode.map {
                it.substring(it.lastIndexOf("/") + 1)
            }.toString(),
            gender = response.gender,
            id = response.id,
            image = response.image,
            location = Character.Location(
                name = response.location.name,
                url = response.location.url
            ),
            name =  response.name,
            origin = Character.Origin(
                name = response.origin.name,
                url = response.origin.url
            ),
            species = response.species,
            status = response.status,
            type = response.type
        )
    }
}