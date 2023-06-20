package com.example.rickopediaapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rickopediaapp.data.model.EpisodeKey

@Dao
interface EpisodeKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(episodeKeys: List<EpisodeKey>)

    @Query("SELECT * FROM episode_key WHERE id = :id")
    suspend fun episodeKeyById(id: Int): EpisodeKey

    @Query("DELETE FROM episode_key")
    suspend fun deleteAllEpisodeKeys()
}