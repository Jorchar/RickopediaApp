package com.example.rickopediaapp.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.rickopediaapp.data.local.AppDatabase
import com.example.rickopediaapp.data.model.Character
import com.example.rickopediaapp.data.model.CharacterMapper
import com.example.rickopediaapp.data.model.Episode
import com.example.rickopediaapp.data.remote.GetCharacterResponse
import com.example.rickopediaapp.data.remote.RemoteDataSource
import com.example.rickopediaapp.util.PAGE_SIZE
import com.example.rickopediaapp.util.PREFETCH_DISTANCE
import javax.inject.Inject

class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val appDatabase: AppDatabase
) {
    companion object {
        private const val TAG = "Repository"
    }

    private val characterDao = appDatabase.characterDao()

    @OptIn(ExperimentalPagingApi::class)
    fun charactersPager() = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            prefetchDistance = PREFETCH_DISTANCE
        ),
        remoteMediator = CharacterMediator(appDatabase, remoteDataSource),
        pagingSourceFactory = { characterDao.getCharacters()}
    ).flow

    suspend fun getCharacterById(id: Int): Result<Character> {
        val result = remoteDataSource.getCharacterById(id)
        return try {
            val characterEpisodes = getCharacterEpisodes(result)
            Result.Success(
                CharacterMapper
                    .buildFrom(
                        response = result,
                        episodes = characterEpisodes
                    )
            )
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    private suspend fun getCharacterEpisodes(characterResult: GetCharacterResponse): List<Episode> {
        val range = characterResult.episode.map {
            it.substring(it.lastIndexOf("/") + 1)
        }.toString()
        return try {
            remoteDataSource.getEpisodeList(range)
        } catch (e: Exception) {
            emptyList()
        }
    }
}
