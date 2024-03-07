package com.example.finalproject.presentation.screen.register

import android.util.Log
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentRegisterLayoutBinding
import com.example.finalproject.presentation.adapters.register.RegisterPageRecyclerViewAdapter
import com.example.finalproject.presentation.event.register.RegisterEvent
import com.example.finalproject.presentation.model.register.UserRegisterInformation
import com.example.finalproject.presentation.state.register.RegisterState
import com.example.finalproject.presentation.base.BaseFragment
import com.example.finalproject.presentation.extension.hideView
import com.example.finalproject.presentation.extension.showView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment :
    BaseFragment<FragmentRegisterLayoutBinding>(FragmentRegisterLayoutBinding::inflate) {

    private val registerViewModel: RegisterViewModel by viewModels()

    private lateinit var registerRecyclerAdapter: RegisterPageRecyclerViewAdapter

    override fun bind() {
        bindAdapter()
    }

    override fun bindViewActionListeners() {
        bindBackBtn()
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

    private fun bindAdapter() {
        with(binding) {
            registerRecyclerAdapter = RegisterPageRecyclerViewAdapter(
                onAlreadyRegisteredCLicked = {
                    bindUserAlreadyRegisteredBtn()
                },
                registerUser = { user ->
                    registerUser(user = user)
                })

            rvRegisterPage.adapter = registerRecyclerAdapter
            rvRegisterPage.layoutManager = LinearLayoutManager(context)
        }
    }

    private fun bindUserAlreadyRegisteredBtn() {
        Log.d("RegisterViewModel", "btn pressed")
        registerViewModel.onEvent(RegisterEvent.UserAlreadyExistsPressed)
    }

    private fun registerUser(user: UserRegisterInformation) {
        Log.d("RegisterViewModel", user.toString())
        registerViewModel.onEvent(
            event = RegisterEvent.Register(
                email = user.email,
                password = user.password,
                repeatPassword = user.repeatPassword,
                firstName = user.firstName,
                lastName = user.lastName)
        )
        sendRegisterResult(user = user)
    }

    private fun bindNavigationFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                registerViewModel.navigationFlow.collect { event ->
                    when (event) {
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
                }
            }
        }
    }

    private fun handleRegisterState(state: RegisterState) {
        with(binding) {
            if (state.isLoading) {
                showView(progressBar)
            }

            if (state.isSuccess) {
                showSuccess()
                hideView(rvRegisterPage)
                hideView(progressBar)
            }

            state.errorMessage?.let { errorMessage ->
                showError(errorMessage = errorMessage)
                hideView(progressBar)
                registerViewModel.onEvent(RegisterEvent.ResetFlow)
            }
        }
    }

    private fun sendRegisterResult(user : UserRegisterInformation) {
        setFragmentResult("registerResult", bundleOf(
                "email" to user.email,
                "password" to user.password
            )
        )
    }

    private fun showSuccess() {
        Snackbar.make(binding.root, "Registration successful", Snackbar.LENGTH_LONG).show()
    }

    private fun showError(errorMessage: String) {
        Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG).setAction("OK") {}.show()
    }

    private fun navigateBack() {
        findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
    }

    private fun navigateUserToLogin() {
        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
    }
}