package com.gokmenmutlu.example4ccyptoapp.repository

import com.gokmenmutlu.example4ccyptoapp.room.FavoritesDao
import com.gokmenmutlu.example4ccyptoapp.room.FavoritesModel
import javax.inject.Inject

class FavoriteRepository @Inject constructor(
    private val favoriteDao: FavoritesDao
) {

    suspend fun addFavoriteCoin(favoritesModel: FavoritesModel) {
        favoriteDao.insertFavoriteCoin(favoritesModel)
    }

    suspend fun getAllFavoriteCoins() : List<FavoritesModel> {
        return favoriteDao.getAllFavoriteCoins()
    }

    suspend fun removeFavoriteCoin(symbol: String) {
        favoriteDao.deleteFavoriteCoinByPairNormalized(symbol)
    }

    suspend fun isCoinFavorite(symbol: String): Boolean {
        return favoriteDao.checkIfCoinIsFavorite(symbol) > 0
    }

}