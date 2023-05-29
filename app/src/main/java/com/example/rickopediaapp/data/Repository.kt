package com.example.rickopediaapp.data

import com.example.rickopediaapp.data.model.Character
import com.example.rickopediaapp.data.remote.RemoteDataSource
import javax.inject.Inject

class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    companion object{
        private const val TAG = "Repository"
    }
    suspend fun getCharacterById(id: Int): Result<Character>{
        return try {
            Result.Success(remoteDataSource.getCharacterById(id))
        }catch (e:Exception){
            Result.Error(e)
        }
    }
}