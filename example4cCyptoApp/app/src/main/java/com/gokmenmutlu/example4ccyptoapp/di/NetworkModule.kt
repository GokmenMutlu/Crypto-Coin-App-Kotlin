package com.gokmenmutlu.example4ccyptoapp.di

import com.gokmenmutlu.example4ccyptoapp.service.ApiServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)   // -> Bu Module, Tüm uygulama boyunca geçerli olacak.
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.btcturk.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}