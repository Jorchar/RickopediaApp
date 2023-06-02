package com.example.rickopediaapp.data.model

data class GetCharactersPageResponse(
    val info: Info = Info(),
    val results: List<Character>
) {

    data class Info(
        val count: Int = 0,
        val pages: Int = 0,
        val next: String? = null,
        val prev: String? = null
    )
}