package com.example.finalproject.presentation.screen.session

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentSessionLayoutBinding
import com.example.finalproject.presentation.base.BaseFragment
import com.example.finalproject.presentation.event.welcome.SessionEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SessionFragment : BaseFragment<FragmentSessionLayoutBinding>(FragmentSessionLayoutBinding::inflate) {

    private val sessionViewModel : SessionViewModel by viewModels()

    override fun bindViewActionListeners() {
        bindSession()
    }

    override fun bindObservers() {
        bindNavigationFlow()
    }

    private fun bindSession() {
        sessionViewModel.onEvent(SessionEvent.CheckCurrentSession)
    }

    private fun bindNavigationFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                sessionViewModel.navigationFlow.collect() { event ->
                    handleEvent(event)
                }
            }
        }
    }

    private fun handleEvent(event : SessionNavigationEvent) {
        when(event) {
            SessionNavigationEvent.SessionExists -> {
                navigateToHomePage()
            }
            SessionNavigationEvent.SessionMissing -> {
                navigateToWelcomePage()
            }
        }
    }

    private fun navigateToHomePage() {
        findNavController().navigate(R.id.action_sessionFragment_to_stockHomeFragment)
    }

    private fun navigateToWelcomePage() {
        findNavController().navigate(R.id.action_sessionFragment_to_welcomeFragment)
    }
}