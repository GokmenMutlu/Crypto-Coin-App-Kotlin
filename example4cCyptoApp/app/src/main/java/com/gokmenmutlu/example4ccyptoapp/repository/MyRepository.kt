package com.gokmenmutlu.example4ccyptoapp.repository

import com.gokmenmutlu.example4ccyptoapp.model.CryptoApiModel
import com.gokmenmutlu.example4ccyptoapp.service.ApiServices
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyRepository @Inject constructor(
    private val retrofit: Retrofit,
    private val favoriteRepository: FavoriteRepository? = null
) {

    private val apiService = retrofit.create(ApiServices::class.java)

    suspend fun getDataFromApiFilterTRY() : List<CryptoApiModel.Data?>? {
        return try {
            val response = apiService.getCrypto()
            response.data?.filter { it?.denominatorSymbol == "TRY" }
        } catch (e: Exception) {
            println(e.toString())
            null
        }
    }
    suspend fun getDataFromApiFilterUSD() : List<CryptoApiModel.Data?>? {
        return try {
            val response = apiService.getCrypto()
            response.data?.filter { it?.denominatorSymbol == "USDT" }
        } catch (e: Exception) {
            println(e.toString())
            null
        }
    }

    suspend fun getDataFromApiForFavorites(): List<CryptoApiModel.Data?>? {
        return try {
            // Eğer favoriteRepository null değilse, favori sembollerini al
            val favoriteSymbols = favoriteRepository?.getAllFavoriteCoins()?.map { it.cryptoSymbol } ?: emptyList()

            // API'den tüm verileri çek
            val response = apiService.getCrypto()

            // Eğer favoriteSymbols boş değilse, sadece favori sembollerini filtrele
            response.data?.filter { it?.pairNormalized?.let { pair ->
                favoriteSymbols.contains(pair) // Eğer pair, favori semboller arasında varsa
            } == true }
        } catch (e: Exception) {
            println(e.toString())
            null
        }
    }

    }



