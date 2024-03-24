package com.example.finalproject.presentation.screen.session

import android.app.AlertDialog
import android.content.Intent
import androidx.core.os.bundleOf
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
import android.provider.Settings


@AndroidEntryPoint
class SessionFragment : BaseFragment<FragmentSessionLayoutBinding>(FragmentSessionLayoutBinding::inflate) {

    private val sessionViewModel : SessionViewModel by viewModels()

    override fun bind() {
        extractSymbol()
    }

    override fun bindViewActionListeners() {

    }

    override fun bindObservers() {
        bindNavigationFlow()
    }

    private fun bindNavigationFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                sessionViewModel.navigationFlow.collect { event ->
                    handleEvent(event)
                }
            }
        }
    }

    private fun handleEvent(event : SessionNavigationEvent) {
        when(event) {
            is SessionNavigationEvent.SessionExists -> {
                navigateToHomePage()
            }
            is SessionNavigationEvent.SessionMissing -> {
                navigateToWelcomePage()
            }
            is SessionNavigationEvent.NoNetworkConnection -> {
                promptToCheckNetwork()
            }
        }
    }

    private fun navigateToHomePage() {
        findNavController().navigate(R.id.action_sessionFragment_to_stockHomeFragment)
    }

    private fun navigateToWelcomePage() {
        findNavController().navigate(R.id.action_sessionFragment_to_welcomeFragment)
    }

    private fun promptToCheckNetwork() {
        AlertDialog.Builder(context)
            .setTitle("No Internet Connection")
            .setMessage("Please check your network settings and try again.")
            .setPositiveButton("Retry") { _, _ ->
                sessionViewModel.onEvent(SessionEvent.CheckCurrentSession)
            }
            .setNegativeButton("Turn on WiFi") { _, _ ->
                startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
            }
            .setCancelable(false)
            .show()
    }

    private fun extractSymbol() {
        val symbol = arguments?.getString("symbol")
        symbol?.let {
            val bundle = bundleOf("symbol" to it)
            findNavController().navigate(
                R.id.action_sessionFragment_to_companyDetailsFragment,
                bundle
            )
        }
    }
}