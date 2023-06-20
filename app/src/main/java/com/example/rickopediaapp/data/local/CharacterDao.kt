package com.example.rickopediaapp.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rickopediaapp.data.model.Character

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacters(characters: List<Character>)

    @Query("SELECT * FROM characters WHERE id LIKE :characterId")
    fun getCharacterById(characterId: Int): Character

    @Query("SELECT * FROM characters")
    fun getCharacters(): PagingSource<Int, Character>

    @Query("DELETE FROM characters")
    fun deleteAllCharacters()
}