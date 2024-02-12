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

    private val stockHomeViewModel : StockHomeViewModel by viewModels()
    private val args: StockHomeFragmentArgs by navArgs()
    private lateinit var uid: String

    override fun bind() {
        bindUserUid()
    }

    override fun bindViewActionListeners() {
        bindBackBtn()
    }

    override fun bindObservers() {
        bindNavigationFlow()
    }

    private fun bindUserUid() {
        uid = args.uid
        if (uid.isEmpty()) {
            bindUid()
        }
    }

    private fun bindBackBtn() {
        with(binding) {
            btnBack.setOnClickListener {
                stockHomeViewModel.onEvent(StockHomeEvent.LogOut)
            }
        }
    }

    private fun bindUid() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                uid = stockHomeViewModel.getUidFromDataStore().first()
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