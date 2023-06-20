package com.example.rickopediaapp.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rickopediaapp.data.model.Episode

@Dao
interface EpisodeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEpisodes(episodes: List<Episode>)

    @Query("SELECT * FROM episodes WHERE id IN (:query)")
    fun getEpisodes(query: List<Int>): PagingSource<Int, Episode>

    @Query("DELETE FROM episodes WHERE id IN (:query)")
    fun deleteEpisodes(query: List<Int>)

    @Query("DELETE FROM episodes")
    fun clearAll()
}