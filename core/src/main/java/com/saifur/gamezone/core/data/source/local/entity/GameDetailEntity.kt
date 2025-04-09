package com.saifur.gamezone.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_detail")
data class GameDetailEntity (
    @PrimaryKey
    var id: Int,

    var name: String,

    @ColumnInfo(name = "background_image")
    var backgroundImage: String,

    var category: String,

    var rating: Double,

    var description: String,

    @ColumnInfo(name = "release_date")
    var releaseDate: String,

    @ColumnInfo(name = "last_update")
    var lastUpdate: String,

    var developers: String,

    var platforms: String,

    var website: String,

    var reddit: String,

    var metacritic: String,

    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean,
)