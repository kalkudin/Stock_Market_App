package com.example.finalproject.presentation.bottom_sheets.existing_cards

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.databinding.BottomSheetChooseExistingCardLayoutBinding
import com.example.finalproject.presentation.adapters.funds.CreditCardRecyclerAdapter
import com.example.finalproject.presentation.base.BaseBottomSheet
import com.example.finalproject.presentation.bottom_sheets.event.GetUserCardsEvent
import com.example.finalproject.presentation.bottom_sheets.state.UserCardsState
import com.example.finalproject.presentation.extension.addOnHorizontalScrollListener
import com.example.finalproject.presentation.model.funds.CreditCard
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserCreditCardsBottomSheetFragment : BaseBottomSheet<BottomSheetChooseExistingCardLayoutBinding>(BottomSheetChooseExistingCardLayoutBinding::inflate){

    private val userCreditCardsViewModel : UserCreditCardsViewModel by viewModels()

    private lateinit var creditCardRecyclerAdapter : CreditCardRecyclerAdapter

    override fun bind() {
        bindCreditCards()
        bindCreditCardsAdapter()
    }

    override fun bindViewActionListeners() {
    }

    override fun bindObserves() {
        bindCardsFlow()
        bindNavigationFlow()
    }

    private fun bindCreditCards() {
        userCreditCardsViewModel.onEvent(GetUserCardsEvent.GetUserCreditCards)
    }

    private fun bindCreditCardsAdapter() {
        with(binding) {
            creditCardRecyclerAdapter = CreditCardRecyclerAdapter { creditCard ->
                handleCardClicked(creditCard)
            }
            rvCards.apply {
                adapter = creditCardRecyclerAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }


    private fun bindCardsFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                userCreditCardsViewModel.creditCardFlow.collect { state ->
                    handleState(state = state)
                }
            }
        }
    }

    private fun handleState(state : UserCardsState) {
        with(binding) {
            if(state.isLoading) progressBar.visibility = View.VISIBLE

            state.errorMessage?.let {
                    errorMessage -> handleErrorMessage(errorMessage = errorMessage)
                progressBar.visibility = View.GONE}

            state.cardList?.let { list ->
                creditCardRecyclerAdapter.submitList(list)
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun bindNavigationFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                userCreditCardsViewModel.navigationFlow.collect { event ->
                    when(event) {
                        is CreditCardsNavigationFlow.NavigateBack -> handleNavigation(card = event.creditCard)
                    }
                }
            }
        }
    }

    private fun handleCardClicked(card : CreditCard) {
        userCreditCardsViewModel.onEvent(GetUserCardsEvent.NavigateBack(card = card))
    }

    private fun handleNavigation(card : CreditCard) {
        val action = UserCreditCardsBottomSheetFragmentDirections.actionUserCreditCardsBottomSheetFragmentToUserFundsFragment(card)
        findNavController().navigate(action)
    }

    private fun handleErrorMessage(errorMessage : String) {
        Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG).setAction("OK"){}.show()
    }
}