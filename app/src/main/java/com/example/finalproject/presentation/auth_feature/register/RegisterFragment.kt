package com.example.finalproject.presentation.auth_feature.register

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentRegisterLayoutBinding
import com.example.finalproject.presentation.auth_feature.event.RegisterEvent
import com.example.finalproject.presentation.auth_feature.model.RegisterState
import com.example.finalproject.presentation.base.BaseFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterLayoutBinding>(FragmentRegisterLayoutBinding::inflate){

    private val registerViewModel : RegisterViewModel by viewModels()

    override fun bindViewActionListeners() {
        bindBackBtn()
        bindUserAlreadyRegisteredBtn()
        bindRegisterBtn()
    }

    override fun bindObservers() {
        bindNavigationFlow()
        bindRegisterFlow()
    }

    private fun bindBackBtn() {
        with(binding) {
            btnBack.setOnClickListener {
                registerViewModel.onEvent(RegisterEvent.BackPressed)
            }
        }
    }

    private fun bindUserAlreadyRegisteredBtn() {
        with(binding) {
            btnAlreadyRegistered.setOnClickListener {
                registerViewModel.onEvent(RegisterEvent.UserAlreadyExistsPressed)
            }
        }
    }

    private fun bindRegisterBtn() {
        with(binding) {
            btnRegister.setOnClickListener {
                registerViewModel.onEvent(event = RegisterEvent.Register(
                    email = etEmail.text.toString(),
                    password = etPassword.text.toString(),
                    repeatPassword = etRepeatPassword.text.toString()
                ))
            }
        }
    }

    private fun bindNavigationFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                registerViewModel.navigationFlow.collect { event ->
                    when(event) {
                        is RegisterNavigationEvent.NavigateToHome -> navigateBack()
                        is RegisterNavigationEvent.NavigateToLogin -> navigateUserToLogin()
                    }
                }
            }
        }
    }

    private fun bindRegisterFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                registerViewModel.registerFlow.collect { state ->
                    handleRegisterState(state)
                    registerViewModel.onEvent(RegisterEvent.ResetFlow)
                }
            }
        }
    }

    private fun handleRegisterState(state: RegisterState) {
        binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

        if(state.isSuccess) {
            showSuccess()
        }

        state.errorMessage?.let { errorMessage ->
            showError(errorMessage = errorMessage)
        }
    }

    private fun showSuccess(){
        Snackbar.make(binding.root, "Registration successful", Snackbar.LENGTH_LONG).show()
    }

    private fun showError(errorMessage : String){
        Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG).setAction("OK") {}.show()
    }

    private fun navigateBack() {
        findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
    }

    private fun navigateUserToLogin() {
        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
    }
}