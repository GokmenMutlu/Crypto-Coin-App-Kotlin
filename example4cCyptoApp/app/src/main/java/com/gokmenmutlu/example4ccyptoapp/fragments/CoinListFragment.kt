package com.gokmenmutlu.example4ccyptoapp.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gokmenmutlu.example4ccyptoapp.R
import com.gokmenmutlu.example4ccyptoapp.adapter.CryptoAdapter
import com.gokmenmutlu.example4ccyptoapp.databinding.FragmentCoinListBinding
import com.gokmenmutlu.example4ccyptoapp.model.CryptoApiModel
import com.gokmenmutlu.example4ccyptoapp.utils.DateUtils
import com.gokmenmutlu.example4ccyptoapp.viewmodel.CryptoViewModel
import com.gokmenmutlu.example4ccyptoapp.viewmodel.FavoriteViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CoinListFragment : Fragment() {

    private var _binding: FragmentCoinListBinding? = null
    private val binding get() = _binding!!
    private lateinit var cryptoViewModel: CryptoViewModel
    private val adapter = CryptoAdapter(arrayListOf())
    private var infoDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCoinListBinding.inflate(inflater, container, false)
        val view = binding.root

        cryptoViewModel = ViewModelProvider(this).get(CryptoViewModel::class.java)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cryptoViewModel.getCryptoDataTRY()  //-> START

        //cryptoViewModel.cryptoData observer
        observeCryptoData()

        //RecyclerView
        binding.recyclerViewCryptoList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewCryptoList.adapter = adapter

        //TRY and USD button filter
        binding.buttonTRY.setOnClickListener { buttonTRY() }
        binding.buttonDollar.setOnClickListener { buttonUSD() }

        setupBottomNavigationBar() //-> BottomNavigationBar
        setupSearchListener()   //-> SearchTxt Filter

        binding.imageSettingsButton.setOnClickListener {
            showInfoDialog("Settings is coming soon...\n\nVeriler BTCTURK 'ten cekilmektedir. \nSaat kacta cekildiği arama çubuğunun altında yazmaktadir. \n(UTC+3 TR)")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeCryptoData() {
        cryptoViewModel.cryptoData.observe(viewLifecycleOwner) { data ->
            data?.let {
                if (it.isNotEmpty()) {
                    binding.recyclerViewCryptoList.visibility = View.VISIBLE
                    adapter.updateCryptoList(it)
                    it[0].timestamp?.let { time ->
                        binding.editTextTime.setText(DateUtils.setTimeText(time))
                    }
                } else {
                    binding.editTextTime.text = null
                    binding.recyclerViewCryptoList.visibility = View.GONE
                }
            }
        }
    }

    private fun buttonTRY() {
        cryptoViewModel.getCryptoDataTRY()
        binding.apply {
            buttonDollar.setBackgroundResource(R.drawable.button_off_background)
            buttonTRY.setBackgroundResource(R.drawable.button_on_background)
            buttonTRY.setTextColor(Color.WHITE)
            buttonDollar.setTextColor(Color.BLACK)
            searchTxt.text = null
        }
    }

    private fun buttonUSD() {
        cryptoViewModel.getCryptoDataUSD()
        binding.apply {
            buttonDollar.setBackgroundResource(R.drawable.button_on_background)
            buttonTRY.setBackgroundResource(R.drawable.button_off_background)
            buttonTRY.setTextColor(Color.BLACK)
            buttonDollar.setTextColor(Color.WHITE)
            searchTxt.text = null
        }
    }

    private fun showCurrencyButtons() {
        binding.apply {
            buttonTRY.visibility = View.VISIBLE
            buttonDollar.visibility = View.VISIBLE
            buttonTRY.setBackgroundResource(R.drawable.button_on_background)
            buttonDollar.setBackgroundResource(R.drawable.button_off_background)
            buttonTRY.setTextColor(Color.WHITE)
            buttonDollar.setTextColor(Color.BLACK)
            searchTxt.text = null
        }
    }

    private fun setupBottomNavigationBar() {
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    cryptoViewModel.getCryptoDataTRY()
                    showCurrencyButtons()
                    true
                }

                R.id.nav_favorites -> {
                    binding.buttonTRY.visibility = View.GONE
                    binding.buttonDollar.visibility = View.GONE
                    cryptoViewModel.getFavoriteCoins()
                    true
                }

                else -> false
            }
        }
    }

    private fun setupSearchListener() {
        binding.searchTxt.addTextChangedListener { editable ->
            val query = editable.toString()
            cryptoViewModel.getSearchCoins(query)
        }
    }

    private fun showInfoDialog(message: String) {
        if (infoDialog == null) {
            val builder = AlertDialog.Builder(requireContext())
            builder.setMessage(message)
            builder.setCancelable(true) // Kullanıcı iptal edebilir.
            infoDialog = builder.create()

            infoDialog?.window?.setBackgroundDrawableResource(R.drawable.layout_background)
        }
        if (!infoDialog!!.isShowing) {
            infoDialog!!.setMessage(message)
            infoDialog!!.show()
        }
    }

}