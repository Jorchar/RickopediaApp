package com.example.rickopediaapp.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickopediaapp.data.model.Character

class CharactersPagingSource(
    private val remoteDataSource: RemoteDataSource
) : PagingSource<Int, Character>() {
    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.minus(1) ?: anchorPage?.nextKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> =
        try {
            val nextPageNumber = params.key ?: 1
            val response = remoteDataSource.getCharacterPage(nextPageNumber)
            LoadResult.Page(
                data = response.results,
                prevKey = null,
                nextKey = if (response.results.isNotEmpty()) nextPageNumber.plus(1) else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

}