package com.example.rickopediaapp.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.rickopediaapp.data.model.Character
import com.example.rickopediaapp.data.remote.CharactersPagingSource
import com.example.rickopediaapp.data.remote.RemoteDataSource
import com.example.rickopediaapp.util.PAGE_SIZE
import com.example.rickopediaapp.util.PREFETCH_DISTANCE
import javax.inject.Inject

class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    companion object {
        private const val TAG = "Repository"
    }

    fun charactersPager() = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            prefetchDistance = PREFETCH_DISTANCE
        )
    ) {
        CharactersPagingSource(remoteDataSource)
    }.flow

    suspend fun getCharacterById(id: Int): Result<Character> {
        return try {
            Result.Success(remoteDataSource.getCharacterById(id))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
