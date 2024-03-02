package com.example.finalproject.presentation.stock_feature.funds

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentFundsLayoutBinding
import com.example.finalproject.presentation.base.BaseFragment
import com.example.finalproject.presentation.stock_feature.funds.event.UserFundsEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserFundsFragment : BaseFragment<FragmentFundsLayoutBinding>(FragmentFundsLayoutBinding::inflate) {

    private val fundsViewModel : UserFundsViewModel by viewModels()

    override fun bind() {
        bindBackBtn()
    }

    override fun bindViewActionListeners() {

    }

    override fun bindObservers() {
        bindNavigationFlow()
    }

    private fun bindBackBtn() {
        with(binding) {
            btnBack.setOnClickListener {
                fundsViewModel.onEvent(UserFundsEvent.BackButtonPressed)
            }
        }
    }

    private fun bindNavigationFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                fundsViewModel.navigationFlow.collect { event ->
                    when(event) {
                        is FundsNavigationEvent.NavigateBack -> navigateBack()
                    }
                }
            }
        }
    }

    private fun navigateBack() {
        findNavController().navigate(R.id.action_userFundsFragment_to_stockHomeFragment)
    }
}