package com.gokmenmutlu.example4ccyptoapp.fragments

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.gokmenmutlu.example4ccyptoapp.R
import com.gokmenmutlu.example4ccyptoapp.databinding.FragmentDetailsBinding
import com.gokmenmutlu.example4ccyptoapp.model.CryptoApiModel
import com.gokmenmutlu.example4ccyptoapp.utils.DateUtils
import com.gokmenmutlu.example4ccyptoapp.utils.IconUtils
import com.gokmenmutlu.example4ccyptoapp.viewmodel.FavoriteViewModel
import kotlinx.coroutines.launch
import java.text.DecimalFormat


class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val formatter = DecimalFormat("###,###,##0.######") //->Price format
    private val favoriteViewModel: FavoriteViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cryptoData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable<CryptoApiModel.Data>("sendCryptoData", CryptoApiModel.Data::class.java)
        } else {
            arguments?.getParcelable("sendCryptoData") // <-Deprecated !
        }

        cryptoData?.let {
            bindingOperations(it)
            checkIfCoinIsFavorite(it)

            binding.favoriteImageButton.setOnClickListener { view ->
                favoriteCoinAddRemoveButton(it)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun bindingOperations(data: CryptoApiModel.Data) {
        val cryptoName = data.numeratorSymbol
        val currency = data.denominatorSymbol
        val iconResId = IconUtils.getIcon(cryptoName)

        val dailyPercent = data.dailyPercent
        val dailyPercentString : String
        if (dailyPercent != null && dailyPercent > 0.0 ) { // Günlük değişim oranı
            dailyPercentString = "(%+ ${data.dailyPercent})"
        } else {
            dailyPercentString = "(% ${data.dailyPercent})"
            binding.textCryptoChangePrice.setTextColor(Color.RED)
        }

        binding.apply {
            imageView2.setImageResource(iconResId)
            textCryptoName.text = "$cryptoName/$currency"
            textCryptoLastPrice.text =    "Last Price: ${formatter.format(data.last)}"
            textCryptoLowPrice.text =     "24h Low: ${formatter.format(data.low)}"
            textCryptoHighPrice.text =    "24h High: ${formatter.format(data.high)}"
            textCryptoAveragePrice.text = "24h Avg: ${formatter.format(data.average)}"
            textCryptoAskPrice.text =     "Ask: ${formatter.format(data.ask)}"
            textCryptoBidPrice.text =     "Bid: ${formatter.format(data.bid)}"
            textCryptoChangePrice.text =  "24h Change: $dailyPercentString"
        }

        data.timestamp?.let { time-> binding.editTextTime.setText(DateUtils.setTimeText(time)) }
    }

    private fun favoriteCoinAddRemoveButton(data: CryptoApiModel.Data) {
        val cryptoSymbol = data.pairNormalized
        cryptoSymbol?.let {
                  // Eğer coin favori listesinde varsa, favorilerden çıkar
            viewLifecycleOwner.lifecycleScope.launch {
                if (favoriteViewModel.checkIsFavorite(it)) {
                    favoriteViewModel.removeFavoriteCoin(it)
                    binding.favoriteImageButton.setBackgroundResource(R.drawable.gray_background) // Favoriden çıkarıldığında
                    Toast.makeText(requireContext(),"Deleted from favorite",Toast.LENGTH_SHORT).show()
                } else {
                    // Eğer coin favori listesinde yoksa, favorilere ekle
                    favoriteViewModel.addFavoriteCoin(it)
                    binding.favoriteImageButton.setBackgroundResource(R.drawable.button_off_background) // Favoriye eklenince
                    Toast.makeText(requireContext(),"FAVORITE",Toast.LENGTH_SHORT).show()
                }
            }
        }

    }


    private fun checkIfCoinIsFavorite(data: CryptoApiModel.Data) {  // for favorite in Start
        val cryptoSymbol = data.pairNormalized
        cryptoSymbol?.let {
            // Coin'in favori olup olmadığını kontrol et
            viewLifecycleOwner.lifecycleScope.launch {
                if (favoriteViewModel.checkIsFavorite(it)) {
                    // Eğer favorilerdeyse, butonun arka planını değiştir
                    binding.favoriteImageButton.setBackgroundResource(R.drawable.button_off_background)
                } else {
                    // Eğer favorilerde değilse, butonun arka planını değiştirme
                    binding.favoriteImageButton.setBackgroundResource(R.drawable.gray_background)
                }
            }
        }
    }



}