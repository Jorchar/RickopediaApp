package com.example.rickopediaapp.data.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "locations")
data class Location(
    @PrimaryKey var id: Int = 0,
    @Ignore var residents: List<String> = emptyList(),
    var name: String = "",
    var type: String = "",
    var dimension: String = "",
    var url: String = "",
    var created: String = ""
)
