package com.gokmenmutlu.example4ccyptoapp.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteCoin(favoriteCoin: FavoritesModel)

    @Query("SELECT * FROM favorites_coins")
    suspend fun getAllFavoriteCoins() : List<FavoritesModel>

    @Query("DELETE FROM favorites_coins WHERE cryptoSymbol = :cryptoSymbol")
    suspend fun deleteFavoriteCoinByPairNormalized(cryptoSymbol: String)

    @Query("SELECT COUNT(*) FROM favorites_coins WHERE cryptoSymbol = :cryptoSymbol")
    suspend fun checkIfCoinIsFavorite(cryptoSymbol: String) : Int

}