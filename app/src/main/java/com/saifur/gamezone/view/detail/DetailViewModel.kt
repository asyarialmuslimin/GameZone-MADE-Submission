package com.saifur.gamezone.view.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saifur.gamezone.core.domain.model.GameDetail
import com.saifur.gamezone.core.domain.usecase.IGameUseCase
import com.saifur.gamezone.core.utils.Resource
import kotlinx.coroutines.launch

class DetailViewModel(private val gameUseCase: IGameUseCase) : ViewModel() {
    val gameDetail = MutableLiveData<Resource<GameDetail?>>()

    fun getGameDetail(id: Int) {
        viewModelScope.launch {
            gameUseCase.getGameDetail(id).collect {
                gameDetail.value = it
            }
        }
    }

    fun editGameFavourite(isFavourited: Boolean, game: GameDetail) {
        val newGame = game.copy()
        newGame.isFavorite = isFavourited
        viewModelScope.launch {
            gameUseCase.updateGame(newGame)
        }
    }
}