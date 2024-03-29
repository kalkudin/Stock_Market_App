package com.example.finalproject.presentation.screen.welcome

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentHomeLayoutBinding
import com.example.finalproject.presentation.base.BaseFragment
import com.example.finalproject.presentation.event.welcome.WelcomeEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WelcomeFragment : BaseFragment<FragmentHomeLayoutBinding>(FragmentHomeLayoutBinding::inflate) {

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
                homeViewModel.onEvent(WelcomeEvent.LoginPressed)
            }
        }
    }

    private fun bindRegisterBtn() {
        with(binding) {
            btnRegister.setOnClickListener {
                homeViewModel.onEvent(WelcomeEvent.RegisterPressed)
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
}