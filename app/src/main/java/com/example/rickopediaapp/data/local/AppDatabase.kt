package com.example.rickopediaapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rickopediaapp.data.model.Character

@Database(entities = [Character::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao() : CharacterDao
}