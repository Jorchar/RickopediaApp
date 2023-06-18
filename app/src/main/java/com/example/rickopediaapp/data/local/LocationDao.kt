package com.example.rickopediaapp.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rickopediaapp.data.model.Location

@Dao
interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLocations(locations: List<Location>)

    @Query("SELECT * FROM locations")
    fun getLocations(): PagingSource<Int, Location>

    @Query("DELETE FROM locations")
    fun clearAll()
}