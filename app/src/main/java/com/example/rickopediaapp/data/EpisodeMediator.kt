package com.example.rickopediaapp.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.rickopediaapp.data.local.AppDatabase
import com.example.rickopediaapp.data.model.Episode
import com.example.rickopediaapp.data.remote.RemoteDataSource
import com.example.rickopediaapp.util.EPISODE_PAGE_SIZE
import com.google.gson.Gson

@OptIn(ExperimentalPagingApi::class)
class EpisodeMediator(
    private val query: List<Int>,
    private val database: AppDatabase,
    private val remoteDataSource: RemoteDataSource
) : RemoteMediator<Int, Episode>() {

    private val episodeDao = database.episodeDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Episode>
    ): MediatorResult {
        val gson = Gson()
        try {
            val episodesToLoad = when (loadType) {
                LoadType.REFRESH ->
                    gson.toJson(query.subList(0, EPISODE_PAGE_SIZE.coerceAtMost(query.size)))

                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)


                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                    val startIndex = findIndex(lastItem.id) + 1
                    val endIndex = startIndex + EPISODE_PAGE_SIZE
                    val idsToLoad = query.subList(startIndex, endIndex.coerceAtMost(query.size))
                    gson.toJson(idsToLoad)
                }
            }

            val response = remoteDataSource.getEpisodeList(episodesToLoad)

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    episodeDao.deleteEpisodes(query)
                }
                episodeDao.insertEpisodes(response)
            }

            return MediatorResult.Success(
                endOfPaginationReached = false
            )
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private fun findIndex(target: Int): Int {
        for(i in query.indices){
            if(query[i] == target){
                return  i
            }
        }
        return -1
    }
}