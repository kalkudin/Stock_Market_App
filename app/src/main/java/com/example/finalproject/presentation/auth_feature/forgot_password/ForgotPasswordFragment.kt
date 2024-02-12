package com.example.finalproject.presentation.auth_feature.forgot_password

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentForgotPasswordLayoutBinding
import com.example.finalproject.presentation.auth_feature.event.ForgotPasswordEvent
import com.example.finalproject.presentation.auth_feature.state.ForgotPasswordState
import com.example.finalproject.presentation.base.BaseFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ForgotPasswordFragment : BaseFragment<FragmentForgotPasswordLayoutBinding>(FragmentForgotPasswordLayoutBinding::inflate){

    private val forgotPasswordViewModel : ForgotPasswordViewModel by viewModels()

    override fun bindViewActionListeners() {
        bindBackBtn()
        bindBackToLoginBtn()
        bindPasswordResetBtn()
    }

    override fun bindObservers() {
        bindNavigationFlow()
        bindForgotPasswordFlow()
    }

    private fun bindPasswordResetBtn() {
        with(binding) {
            btnSendResetEmail.setOnClickListener {
                forgotPasswordViewModel.onEvent(ForgotPasswordEvent.ResetPassword(
                    email = etEmail.text.toString())
                )
            }
        }
    }

    private fun bindBackBtn() {
        with(binding) {
            btnBack.setOnClickListener {
                forgotPasswordViewModel.onEvent(ForgotPasswordEvent.BackPressed)
            }
        }
    }

    private fun bindBackToLoginBtn() {
        with(binding) {
            btnFinishedReset.setOnClickListener {
                forgotPasswordViewModel.onEvent(ForgotPasswordEvent.BackToLoginPressed)
            }
        }
    }

    private fun bindNavigationFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                forgotPasswordViewModel.navigationFlow.collect { event ->
                    when (event) {
                        is ForgotPasswordNavigationEvent.GoToLogin -> navigateToLogin()
                        ForgotPasswordNavigationEvent.GoToHome -> navigateToHome()
                    }
                }
            }
        }
    }

    private fun bindForgotPasswordFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                forgotPasswordViewModel.forgotPasswordFlow.collect { state ->
                    handleForgotPasswordState(state)
                }
            }
        }
    }

    private fun handleForgotPasswordState(state: ForgotPasswordState) {
        binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

        if (state.isSuccess) {
            showSuccessMessage()
        }

        state.errorMessage?.let { errorMessage ->
            showErrorMessage(errorMessage)
        }
    }


    private fun showSuccessMessage() {
        Snackbar.make(binding.root,"Reset link sent to your email.", Snackbar.LENGTH_LONG).show()
    }

    private fun showErrorMessage(errorMessage: String) {
        Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG).setAction("OK") {}.show()
    }

    private fun navigateToLogin() {
        findNavController().navigate(R.id.action_forgotPasswordFragment_to_loginFragment)
    }

    private fun navigateToHome() {
        findNavController().navigate(R.id.action_forgotPasswordFragment_to_homeFragment)
    }
}