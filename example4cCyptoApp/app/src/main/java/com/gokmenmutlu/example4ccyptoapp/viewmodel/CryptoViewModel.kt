package com.gokmenmutlu.example4ccyptoapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import com.gokmenmutlu.example4ccyptoapp.model.CryptoApiModel
import com.gokmenmutlu.example4ccyptoapp.repository.MyRepository
import com.gokmenmutlu.example4ccyptoapp.service.ApiServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoViewModel
    @Inject constructor(private val repository: MyRepository) : ViewModel() {

        private val _cryptoData = MutableLiveData<List<CryptoApiModel.Data>>()
    val cryptoData : LiveData<List<CryptoApiModel.Data>> get() = _cryptoData

    private var allCoins: List<CryptoApiModel.Data> = emptyList()  // API'den gelen tüm coinler

    fun getCryptoDataTRY() {
        viewModelScope.launch {
            try {
                val response = repository.getDataFromApiFilterTRY()
                response?.let {
                    allCoins = it.filterNotNull() // TRY
                    _cryptoData.postValue(allCoins)
                }

            } catch (e: Exception) {
                println("Error crypto data: ${e.message}")
            }
        }
    }

    fun getCryptoDataUSD() {
        viewModelScope.launch {
            try {
                val response = repository.getDataFromApiFilterUSD()
                response?.let {
                    allCoins = it.filterNotNull() // USD
                    _cryptoData.postValue(allCoins)
                }

            } catch (e: Exception) {
                println("Error crypto data: ${e.message}")
            }
        }
    }

    fun getFavoriteCoins() {
        viewModelScope.launch {
            try {
                val response = repository.getDataFromApiForFavorites()
                response?.let {
                    allCoins = it.filterNotNull()
                    _cryptoData.postValue(allCoins) // Favorites
                }
            } catch (e: Exception) {
                println("Error Favorite Coin: $e")
            }
        }
    }

    fun getSearchCoins(query: String) {
        if(query.isEmpty()){ // Search -> null
            _cryptoData.postValue(allCoins)
        }
        else {
            val filteredList = allCoins.filter {
                it.numeratorSymbol?.contains(query,true) == true  // Arama metnine göre filtreleme
            }
            _cryptoData.postValue(filteredList)  // Filtrelenmiş listeyi gönder
        }
    }




}