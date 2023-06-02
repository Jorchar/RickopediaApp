package com.example.rickopediaapp.data.remote

import com.example.rickopediaapp.data.model.Character
import com.example.rickopediaapp.data.model.GetCharactersPageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyAPI {

    @GET("character/{id}")
    suspend fun getCharacterById(
        @Path("id") id: Int
    ): Response<Character>

    @GET("character")
    suspend fun getCharacterList(
        @Query("page") pageIndex: Int
    ): Response<GetCharactersPageResponse>
}