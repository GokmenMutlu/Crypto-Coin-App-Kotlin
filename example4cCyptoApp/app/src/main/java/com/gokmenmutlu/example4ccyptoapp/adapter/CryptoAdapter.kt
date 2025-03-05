package com.gokmenmutlu.example4ccyptoapp.adapter

import android.icu.text.DecimalFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gokmenmutlu.example4ccyptoapp.R
import com.gokmenmutlu.example4ccyptoapp.databinding.CryptoViewholderBinding
import com.gokmenmutlu.example4ccyptoapp.model.CryptoApiModel
import com.gokmenmutlu.example4ccyptoapp.utils.IconUtils

class CryptoAdapter(private val cryptoList: ArrayList<CryptoApiModel.Data>) : RecyclerView.Adapter<CryptoAdapter.CryptoViewHolder>() {
    class CryptoViewHolder(val binding: CryptoViewholderBinding) : RecyclerView.ViewHolder(binding.root) {

    }
        private var formatter : DecimalFormat? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = CryptoViewholderBinding.inflate(inflater,parent,false)
        formatter = DecimalFormat("###,###,##0.######")

     return CryptoViewHolder(view)
    }

    override fun getItemCount(): Int = cryptoList.size


    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {

            val currentItem = cryptoList[position]
            val currency = if (currentItem.denominatorSymbol == "TRY") "₺" else "$"
            val  cryptoSymbol = currentItem.numeratorSymbol

            holder.binding.textCryptoSymbol.text = cryptoSymbol

            val lastPrice = currentItem.last?.let {
            formatter?.format(it)
            } ?: "N/A" // Eğer değer null ise "N/A" göster

        holder.binding.textPriceCrypto.text = "$lastPrice $currency" //ex: 1000.00 $

        val resId = IconUtils.getIcon(currentItem.numeratorSymbol)
        Glide.with(holder.itemView.context)
            .load(resId)
            .error(R.drawable.minus)  //Görseller Arttırılabilir.
            .into(holder.binding.imageViewSymbol)

        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("sendCryptoData",currentItem)

            Navigation.findNavController(it).navigate(R.id.action_coinListFragment_to_detailsFragment,bundle)
        }
    }

    fun updateCryptoList(newCryptoList: List<CryptoApiModel.Data>) {
        cryptoList.clear()
        cryptoList.addAll(newCryptoList)
        notifyDataSetChanged()
    }

}