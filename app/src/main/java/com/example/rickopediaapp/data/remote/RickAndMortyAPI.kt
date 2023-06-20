package com.example.rickopediaapp.data.remote

import com.example.rickopediaapp.data.model.Episode
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyAPI {

    @GET("character")
    suspend fun getCharacterList(
        @Query("page") pageIndex: Int
    ): Response<GetCharactersPageResponse>

    @GET("episode/{range}")
    suspend fun getEpisodeList(
        @Path("range") range: String
    ): Response<List<Episode>>

}