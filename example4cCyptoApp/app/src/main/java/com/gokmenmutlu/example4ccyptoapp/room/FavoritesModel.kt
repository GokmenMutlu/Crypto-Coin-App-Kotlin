package com.gokmenmutlu.example4ccyptoapp.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites_coins")
data class FavoritesModel(
    @PrimaryKey val cryptoSymbol: String  // api dan PairNormalized i kaydediyoruz. -> Sembolü

)
