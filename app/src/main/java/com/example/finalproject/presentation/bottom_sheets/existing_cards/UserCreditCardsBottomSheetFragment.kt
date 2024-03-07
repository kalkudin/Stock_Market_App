package com.example.finalproject.presentation.bottom_sheets.existing_cards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.finalproject.databinding.BottomSheetChooseExistingCardLayoutBinding
import com.example.finalproject.presentation.adapters.funds.CreditCardRecyclerAdapter
import com.example.finalproject.presentation.bottom_sheets.event.GetUserCardsEvent
import com.example.finalproject.presentation.bottom_sheets.state.UserCardsState
import com.example.finalproject.presentation.model.funds.CreditCard
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserCreditCardsBottomSheetFragment : BottomSheetDialogFragment(){

    private var _binding: BottomSheetChooseExistingCardLayoutBinding? = null
    private val binding get() = _binding!!

    private val userCreditCardsViewModel : UserCreditCardsViewModel by viewModels()

    private lateinit var creditCardRecyclerAdapter : CreditCardRecyclerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = BottomSheetChooseExistingCardLayoutBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindCreditCards()
        bindCreditCardsAdapter()
        bindCardsFlow()
        bindNavigationFlow()
    }

    private fun bindCreditCards() {
        userCreditCardsViewModel.onEvent(GetUserCardsEvent.GetUserCreditCards)
    }

    private fun bindCreditCardsAdapter() {
        creditCardRecyclerAdapter = CreditCardRecyclerAdapter { creditCard ->
            handleCardClicked(creditCard)
        }
        binding.rvCards.adapter = creditCardRecyclerAdapter
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}