package com.saifur.gamezone.view.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saifur.gamezone.core.domain.model.Game
import com.saifur.gamezone.core.domain.usecase.IGameUseCase
import com.saifur.gamezone.core.utils.Resource
import kotlinx.coroutines.launch

class HomeViewModel(private val gameUseCase: IGameUseCase) : ViewModel() {
    val games = MutableLiveData<Resource<List<Game>>>()

    fun getGames() {
        viewModelScope.launch {
            gameUseCase.getAllGames().collect {
                games.value = it
            }
        }
    }

    fun searchGames(query:String) {
        viewModelScope.launch {
            gameUseCase.searchGame(query).collect {
                games.value = it
            }
        }
    }
}