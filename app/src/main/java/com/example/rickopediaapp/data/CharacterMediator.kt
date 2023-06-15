package com.example.rickopediaapp.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.rickopediaapp.data.local.AppDatabase
import com.example.rickopediaapp.data.model.Character

@OptIn(ExperimentalPagingApi::class)
class CharacterMediator(
    private val database: AppDatabase,
    private val repository: Repository
) : RemoteMediator<Int, Character>() {

    private val characterDao = database.characterDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Character>,
    ): MediatorResult {
        try {
            val pageKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    lastItem?.id ?: return MediatorResult.Success(endOfPaginationReached = true)
                }
            }
            val response = repository.getCharacterPage(pageKey)

            characterDao.insertCharacters(response)

            return MediatorResult.Success(
                endOfPaginationReached = response.isNullOrEmpty()
            )
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }
}