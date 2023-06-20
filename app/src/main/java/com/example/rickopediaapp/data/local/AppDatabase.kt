package com.example.rickopediaapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rickopediaapp.data.model.Character
import com.example.rickopediaapp.data.model.CharacterKey
import com.example.rickopediaapp.data.model.Episode
import com.example.rickopediaapp.data.model.EpisodeKey
import com.example.rickopediaapp.data.model.Location

@Database(entities = [Character::class, CharacterKey::class, Episode::class, EpisodeKey::class, Location::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao() : CharacterDao

    abstract fun characterKeyDao() : CharacterKeyDao

    abstract fun episodeDao() : EpisodeDao

    abstract fun episodeKeyDao(): EpisodeKeyDao

    abstract fun locationDao() : LocationDao
}