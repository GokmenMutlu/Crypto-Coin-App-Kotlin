package com.gokmenmutlu.example4ccyptoapp.utils

import com.gokmenmutlu.example4ccyptoapp.R
 
object IconUtils {

     // GÖRSELLER ÇOĞALTILABILIR. API içerisinden gelmiyor.
        private val ICON_MAP = mapOf(
            "BTC" to R.drawable.bitcoin,
            "ETH" to R.drawable.etherium,
            "SHIB" to R.drawable.shiba,
            "TRX" to R.drawable.trox,
            )

        fun getIcon(symbol: String?): Int {
            return ICON_MAP[symbol] ?: R.drawable.binance
        }

}