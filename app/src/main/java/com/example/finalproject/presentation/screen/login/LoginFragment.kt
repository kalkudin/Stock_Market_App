package com.example.finalproject.presentation.screen.login

import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentLoginLayoutBinding
import com.example.finalproject.presentation.base.BaseFragment
import com.example.finalproject.presentation.event.login.LoginEvent
import com.example.finalproject.presentation.extension.hideView
import com.example.finalproject.presentation.extension.setupPasswordToggle
import com.example.finalproject.presentation.extension.showView
import com.example.finalproject.presentation.state.login.LoginState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginLayoutBinding>(FragmentLoginLayoutBinding::inflate){

    private val loginViewModel : LoginViewModel by viewModels()

    override fun bind() {
        bindPasswordVisibility()
    }

    override fun bindViewActionListeners() {
        bindBackBtn()
        bindLoginBtn()
        bindForgotPassword()
        bindUserNotAuthenticated()
        retrieveAndSetRegisterResult()
    }

    override fun bindObservers() {
        bindNavigationFlow()
        bindLoginFlow()
    }

    private fun bindPasswordVisibility(){
        with(binding) {
            etPassword.setupPasswordToggle()
        }
    }

    private fun bindBackBtn() {
        with(binding) {
            btnBack.setOnClickListener {
                loginViewModel.onEvent(LoginEvent.BackPressed)
            }
        }
    }

    private fun bindForgotPassword() {
        with(binding) {
            btnForgotPassword.setOnClickListener {
                loginViewModel.onEvent(LoginEvent.ForgotPasswordPressed)
            }
        }
    }

    private fun bindUserNotAuthenticated() {
        with(binding) {
            btnNotRegistered.setOnClickListener {
                loginViewModel.onEvent(LoginEvent.UserNotAuthenticatedPressed)
            }
        }
    }

    private fun bindLoginBtn() {
        with(binding) {
            btnLogin.setOnClickListener {
                if (btnRememberMe.isChecked) {
                    loginViewModel.onEvent(LoginEvent.LoginAndRememberUser(email = etEmail.text.toString(), password = etPassword.text.toString()))
                    return@setOnClickListener
                }
                loginViewModel.onEvent(LoginEvent.Login(email = etEmail.text.toString(), password = etPassword.text.toString()))
            }
        }
    }

    private fun bindNavigationFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.navigationFlow.collect { event ->
                    when(event) {
                        is LoginNavigationEvent.NavigateToRegister -> navigateToRegister()
                        is LoginNavigationEvent.NavigateToHome -> navigateToHome()
                        is LoginNavigationEvent.NavigateToForgotPassword -> navigateToForgotPassword()
                        is LoginNavigationEvent.NavigateToStockHome -> navigateToStockHome()
                    }
                }
            }
        }
    }

    private fun bindLoginFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.loginFlow.collect { state ->
                    handleLoginState(state = state)
                }
            }
        }
    }

    private fun handleLoginState(state : LoginState) {
        with(binding) {
            if(state.isLoading) {
                showView(progressBar)
            }

            state.isSuccess?.takeIf { it.isNotEmpty() }?.let {
                showSuccess()
                loginViewModel.onEvent(LoginEvent.LoginSuccess)
            }

            state.errorMessage?.let { errorMessage ->
                showError(errorMessage)
                hideView(progressBar)
                loginViewModel.onEvent(LoginEvent.ResetFlow)
            }
        }
    }

    private fun retrieveAndSetRegisterResult() {
        setFragmentResultListener("registerResult") { _, bundle ->
            binding.etEmail.setText(bundle.getString("email", ""))
            binding.etPassword.setText(bundle.getString("password", ""))
        }
    }

    private fun showSuccess(){
        Snackbar.make(binding.root, "Login Success", Snackbar.LENGTH_LONG).show()
    }

    private fun showError(errorMessage : String){
        Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG).setAction("OK"){}.show()
    }

    private fun navigateToRegister() {
        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
    }

    private fun navigateToHome() {
        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
    }

    private fun navigateToForgotPassword() {
        findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
    }

    private fun navigateToStockHome() {
        if (findNavController().currentDestination?.id == R.id.loginFragment) {
            findNavController().navigate(R.id.action_loginFragment_to_stockHomeFragment)
        }
    }
}