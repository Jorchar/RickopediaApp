package com.example.rickopediaapp.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.rickopediaapp.data.local.AppDatabase
import com.example.rickopediaapp.data.model.Character
import com.example.rickopediaapp.data.remote.RemoteDataSource
import com.example.rickopediaapp.util.CHARACTER_PAGE_SIZE
import com.example.rickopediaapp.util.CHARACTER_PREFETCH_DISTANCE
import com.example.rickopediaapp.util.EPISODE_PAGE_SIZE
import com.example.rickopediaapp.util.EPISODE_PREFETCH_DISTANCE
import com.google.gson.Gson
import javax.inject.Inject

class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val appDatabase: AppDatabase
) {
    companion object {
        private const val TAG = "Repository"
    }

    private val characterDao = appDatabase.characterDao()
    private val episodeDao = appDatabase.episodeDao()
    private val gson = Gson()

    @OptIn(ExperimentalPagingApi::class)
    fun charactersPager() = Pager(
        config = PagingConfig(
            pageSize = CHARACTER_PAGE_SIZE,
            prefetchDistance = CHARACTER_PREFETCH_DISTANCE
        ),
        remoteMediator = CharacterMediator(appDatabase, remoteDataSource),
        pagingSourceFactory = { characterDao.getCharacters() }
    ).flow

    fun getCharacterById(characterId: Int): Character {
        return characterDao.getCharacterById(characterId)
    }

    @OptIn(ExperimentalPagingApi::class)
    fun episodePager(query: String) = Pager(
        config = PagingConfig(
            pageSize = EPISODE_PAGE_SIZE,
            prefetchDistance = EPISODE_PREFETCH_DISTANCE
        ),
        remoteMediator = EpisodeMediator(
            gson.fromJson(query, Array<Int>::class.java).toList(),
            appDatabase,
            remoteDataSource
        ),
        pagingSourceFactory = {
            episodeDao.getEpisodes(
                gson.fromJson(query, Array<Int>::class.java).toList()
            )
        }
    ).flow
}
