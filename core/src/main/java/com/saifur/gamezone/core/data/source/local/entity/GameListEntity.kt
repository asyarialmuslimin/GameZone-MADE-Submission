package com.saifur.gamezone.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_list")
data class GameListEntity(
    @PrimaryKey
    val gameId: Int,

    val name: String,

    @ColumnInfo(name = "background_image")
    val backgroundImage: String,

    val rating: Double,
)