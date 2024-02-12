package com.example.finalproject.presentation.auth_feature.home

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentHomeLayoutBinding
import com.example.finalproject.presentation.auth_feature.event.HomeEvent
import com.example.finalproject.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeLayoutBinding>(FragmentHomeLayoutBinding::inflate) {

    private val homeViewModel : HomeViewModel by viewModels()

    override fun bindViewActionListeners() {
        bindLoginBtn()
        bindRegisterBtn()
    }

    override fun bindObservers() {
        bindNavigationFlow()
    }

    private fun bindLoginBtn() {
        with(binding) {
            btnLogin.setOnClickListener {
                homeViewModel.onEvent(HomeEvent.LoginPressed)
            }
        }
    }

    private fun bindRegisterBtn() {
        with(binding) {
            btnRegister.setOnClickListener {
                homeViewModel.onEvent(HomeEvent.RegisterPressed)
            }
        }
    }

    private fun bindNavigationFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.navigationFlow.collect { event ->
                    when(event) {
                        is HomeNavigationEvent.NavigateToLogin -> navigateToLogin()
                        is HomeNavigationEvent.NavigateToRegister -> navigateToRegister()
                        is HomeNavigationEvent.NavigateToStockHome -> navigateToStockHome()
                    }
                }
            }
        }
    }

    private fun navigateToLogin() {
        findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
    }

    private fun navigateToRegister() {
        findNavController().navigate(R.id.action_homeFragment_to_registerFragment)
    }

    private fun navigateToStockHome() {
        findNavController().navigate(R.id.action_homeFragment_to_stockHomeFragment)
    }
}