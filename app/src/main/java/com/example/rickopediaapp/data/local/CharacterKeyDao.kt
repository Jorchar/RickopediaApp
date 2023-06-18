package com.example.rickopediaapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rickopediaapp.data.model.CharacterKey

@Dao
interface CharacterKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(characterKeys: List<CharacterKey>)

    @Query("SELECT * FROM character_key WHERE id = :id")
    suspend fun characterKeyById(id: Int): CharacterKey

    @Query("DELETE FROM character_key")
    suspend fun deleteAllCharacterKeys()
}