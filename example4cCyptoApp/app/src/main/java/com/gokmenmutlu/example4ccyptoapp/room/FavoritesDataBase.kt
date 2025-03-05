package com.gokmenmutlu.example4ccyptoapp.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoritesModel::class], version = 2)
abstract class FavoritesDataBase : RoomDatabase() {

    abstract fun favoritesDao() : FavoritesDao

}