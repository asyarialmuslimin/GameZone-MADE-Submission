package com.saifur.gamezone.favourite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saifur.gamezone.core.domain.model.FavouriteGame
import com.saifur.gamezone.core.domain.usecase.IGameUseCase
import kotlinx.coroutines.launch

class FavouriteViewModel(private val gameUseCase: IGameUseCase) : ViewModel() {
    val favouriteList = MutableLiveData<List<FavouriteGame>>()

    fun getFavouriteGames() {
        viewModelScope.launch {
            gameUseCase.getFavouriteGames().collect { games ->
                favouriteList.value = games
            }
        }
    }
}