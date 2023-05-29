package com.example.rickopediaapp.data.remote

import com.example.rickopediaapp.data.model.Character
import com.example.rickopediaapp.data.model.Error
import com.google.gson.Gson
import java.lang.RuntimeException
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val gson: Gson,
    private val api: RickAndMortyAPI
) {

    companion object {
        private const val TAG = "RemoteDataSource"
    }

    suspend fun getCharacterById(id: Int): Character {
        val response = api.getCharacterById(id)
        val error = gson.fromJson(response.errorBody().toString(), Error::class.java)
        return response.body() ?: throw RuntimeException(error.msg)
    }
}