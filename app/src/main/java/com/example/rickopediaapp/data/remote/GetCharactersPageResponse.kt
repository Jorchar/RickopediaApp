package com.example.rickopediaapp.data.remote

data class GetCharactersPageResponse(
    val info: Info = Info(),
    val results: List<GetCharacterResponse>
) {

    data class Info(
        val count: Int = 0,
        val pages: Int = 0,
        val next: String? = null,
        val prev: String? = null
    )
}