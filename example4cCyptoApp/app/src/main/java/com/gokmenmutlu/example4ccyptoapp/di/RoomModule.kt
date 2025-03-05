package com.gokmenmutlu.example4ccyptoapp.di

import android.content.Context
import androidx.room.Room
import com.gokmenmutlu.example4ccyptoapp.room.FavoritesDao
import com.gokmenmutlu.example4ccyptoapp.room.FavoritesDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): FavoritesDataBase {
        return Room.databaseBuilder(
            context.applicationContext,
            FavoritesDataBase::class.java,
            "crypto_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideFavoritesDao(database: FavoritesDataBase): FavoritesDao {
        return database.favoritesDao()
    }
}