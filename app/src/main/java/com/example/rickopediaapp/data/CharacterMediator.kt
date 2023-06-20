package com.example.rickopediaapp.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.rickopediaapp.data.local.AppDatabase
import com.example.rickopediaapp.data.model.Character
import com.example.rickopediaapp.data.model.CharacterKey
import com.example.rickopediaapp.data.model.CharacterMapper
import com.example.rickopediaapp.data.remote.RemoteDataSource

@OptIn(ExperimentalPagingApi::class)
class CharacterMediator(
    private val database: AppDatabase,
    private val remoteDataSource: RemoteDataSource
) : RemoteMediator<Int, Character>() {

    private val characterDao = database.characterDao()
    private val characterKeyDao = database.characterKeyDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Character>,
    ): MediatorResult {
        try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val characterKey = getCharacterKeyClosestToCurrentPosition(state)
                    characterKey?.nextPage?.minus(1) ?: 1
                }

                LoadType.PREPEND -> {
                    val characterKey = getCharacterKeyForFirstItem(state)
                    val prevPage = characterKey?.prevPage
                        ?: return MediatorResult.Success(endOfPaginationReached = characterKey != null)
                    prevPage
                }

                LoadType.APPEND -> {
                    val characterKey = getCharacterKeyForLastItem(state)
                    val nextPage = characterKey?.nextPage
                        ?: return MediatorResult.Success(endOfPaginationReached = characterKey != null)
                    nextPage
                }
            }

            val response = remoteDataSource.getCharacterPage(currentPage)
            val endOfPaginationReached = response.results.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    characterKeyDao.deleteAllCharacterKeys()
                    characterDao.deleteAllCharacters()
                }
                val keys = response.results.map { character ->
                    CharacterKey(
                        id = character.id,
                        nextPage = nextPage,
                        prevPage = prevPage
                    )
                }
                characterKeyDao.insertOrReplace(characterKeys = keys)
                characterDao.insertCharacters(characters = response.results.map {
                    CharacterMapper.buildFrom(it)
                })
            }

            return MediatorResult.Success(
                endOfPaginationReached = endOfPaginationReached
            )
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getCharacterKeyClosestToCurrentPosition(
        state: PagingState<Int, Character>
    ): CharacterKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                characterKeyDao.characterKeyById(id)
            }
        }
    }

    private suspend fun getCharacterKeyForFirstItem(
        state: PagingState<Int, Character>
    ): CharacterKey? {
        return state.pages.firstOrNull() { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { character ->
                characterKeyDao.characterKeyById(character.id)
            }
    }

    private suspend fun getCharacterKeyForLastItem(
        state: PagingState<Int, Character>
    ): CharacterKey? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { character ->
                characterKeyDao.characterKeyById(character.id)
            }
    }
}