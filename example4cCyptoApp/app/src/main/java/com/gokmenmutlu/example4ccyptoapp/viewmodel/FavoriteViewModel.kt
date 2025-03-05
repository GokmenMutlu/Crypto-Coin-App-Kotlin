package com.gokmenmutlu.example4ccyptoapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gokmenmutlu.example4ccyptoapp.repository.FavoriteRepository
import com.gokmenmutlu.example4ccyptoapp.room.FavoritesModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val favoriteRepository: FavoriteRepository) : ViewModel() {


    fun addFavoriteCoin(symbol: String) {
        viewModelScope.launch {
                favoriteRepository.addFavoriteCoin(FavoritesModel(symbol))
        }
    }

    fun removeFavoriteCoin(cryptoSymbol:String) {
        viewModelScope.launch {
         favoriteRepository.removeFavoriteCoin(cryptoSymbol)
        }
    }

    suspend fun checkIsFavorite(cryptoSymbol: String): Boolean {
        return favoriteRepository.isCoinFavorite(cryptoSymbol)
    }

}