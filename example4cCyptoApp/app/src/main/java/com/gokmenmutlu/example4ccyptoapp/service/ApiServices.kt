package com.gokmenmutlu.example4ccyptoapp.service

import com.gokmenmutlu.example4ccyptoapp.model.CryptoApiModel
import retrofit2.http.GET

interface ApiServices {

    @GET("api/v2/ticker")
    suspend fun getCrypto() : CryptoApiModel

}