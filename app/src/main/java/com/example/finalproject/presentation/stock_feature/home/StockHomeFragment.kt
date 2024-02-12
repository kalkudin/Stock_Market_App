package com.example.finalproject.presentation.stock_feature.home

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentStockHomeLayoutBinding
import com.example.finalproject.presentation.base.BaseFragment
import com.example.finalproject.presentation.stock_feature.event.StockHomeEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.first


@AndroidEntryPoint
class StockHomeFragment : BaseFragment<FragmentStockHomeLayoutBinding>(FragmentStockHomeLayoutBinding::inflate){
    //if you need to get the uid to save to a database for example, then go into the viewmodel
    //and use the readuid function there directly with .first() to get the first instance from datastore
    //theres no need to get it to the fragment
    private val stockHomeViewModel : StockHomeViewModel by viewModels()

    override fun bindViewActionListeners() {
        bindBackBtn()
    }

    override fun bindObservers() {
        bindNavigationFlow()
    }

    private fun bindBackBtn() {
        with(binding) {
            btnBack.setOnClickListener {
                stockHomeViewModel.onEvent(StockHomeEvent.LogOut)
            }
        }
    }

    private fun bindNavigationFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                stockHomeViewModel.navigationFlow.collect { event ->
                    when(event) {
                        is StockHomeNavigationEvent.LogOut -> logOut()
                    }
                }
            }
        }
    }

    private fun logOut() {
        findNavController().navigate(R.id.action_stockHomeFragment_to_loginFragment)
    }
}