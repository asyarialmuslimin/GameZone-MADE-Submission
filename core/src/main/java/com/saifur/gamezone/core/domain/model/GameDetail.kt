package com.saifur.gamezone.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameDetail(
    var id: Int,
    var name: String,
    var backgroundImage: String,
    var category: String,
    var rating: Double,
    var description: String,
    var releaseDate: String,
    var lastUpdate: String,
    var developers: String,
    var platforms: String,
    var website: String,
    var reddit: String,
    var metacritic: String,
    var isFavorite: Boolean,
) : Parcelable
