package com.example.rickopediaapp.data.remote

import com.example.rickopediaapp.data.model.Character
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RickAndMortyAPI {

    @GET("character/{id}")
    suspend fun getCharacterById(
        @Path("id") id: Int
    ): Response<Character>
}