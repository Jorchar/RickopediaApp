package com.example.rickopediaapp.data.remote

import com.example.rickopediaapp.data.model.Episode
import com.example.rickopediaapp.data.model.Error
import com.google.gson.Gson
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val gson: Gson,
    private val api: RickAndMortyAPI
) {

    companion object {
        private const val TAG = "RemoteDataSource"
    }

    suspend fun getCharacterById(id: Int): GetCharacterResponse {
        val response = api.getCharacterById(id)
        val error = gson.fromJson(response.errorBody().toString(), Error::class.java)
        return response.body() ?: throw RuntimeException(error.msg)
    }

    suspend fun getCharacterPage(pageIndex: Int): GetCharactersPageResponse {
        val response = api.getCharacterList(pageIndex)
        val error = gson.fromJson(response.errorBody().toString(), Error::class.java)
        return response.body() ?: throw RuntimeException(error.msg)
    }



    suspend fun getEpisodeById(id: Int): Episode {
        val response = api.getEpisodeById(id)
        val error = gson.fromJson(response.errorBody().toString(), Error::class.java)
        return response.body() ?: throw RuntimeException(error.msg)
    }

    suspend fun getEpisodeList(range: String): List<Episode> {
        val response = api.getEpisodeList(range)
        val error = gson.fromJson(response.errorBody().toString(), Error::class.java)
        return response.body() ?: throw RuntimeException(error.msg)
    }
}