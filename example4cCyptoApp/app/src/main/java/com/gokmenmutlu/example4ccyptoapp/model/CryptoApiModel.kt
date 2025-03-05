package com.gokmenmutlu.example4ccyptoapp.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


data class CryptoApiModel(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("data")
    val `data`: List<Data?>?,
    @SerializedName("message")
    val message: Any?,
    @SerializedName("success")
    val success: Boolean?
) {
    @Parcelize
    data class Data(
        @SerializedName("ask")
        val ask: Double?,
        @SerializedName("average")
        val average: Double?,
        @SerializedName("bid")
        val bid: Double?,
        @SerializedName("daily")
        val daily: Double?,
        @SerializedName("dailyPercent")
        val dailyPercent: Double?,
        @SerializedName("denominatorSymbol")
        val denominatorSymbol: String?,
        @SerializedName("high")
        val high: Double?,
        @SerializedName("last")
        val last: Double?,
        @SerializedName("low")
        val low: Double?,
        @SerializedName("numeratorSymbol")
        val numeratorSymbol: String?,
        @SerializedName("open")
        val `open`: Double?,
        @SerializedName("order")
        val order: Int?,
        @SerializedName("pair")
        val pair: String?,
        @SerializedName("pairNormalized")
        val pairNormalized: String?,
        @SerializedName("timestamp")
        val timestamp: Long?,
        @SerializedName("volume")
        val volume: Double?
    ) : Parcelable
}