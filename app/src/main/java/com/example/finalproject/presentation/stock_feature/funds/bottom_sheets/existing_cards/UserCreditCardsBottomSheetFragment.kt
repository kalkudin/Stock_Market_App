package com.example.finalproject.presentation.stock_feature.funds.bottom_sheets.existing_cards

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.finalproject.databinding.BottomSheetAddNewCardLayoutBinding
import com.example.finalproject.databinding.BottomSheetChooseExistingCardLayoutBinding
import com.example.finalproject.presentation.stock_feature.funds.adapter.CreditCardRecyclerAdapter
import com.example.finalproject.presentation.stock_feature.funds.bottom_sheets.event.GetUserCardsEvent
import com.example.finalproject.presentation.stock_feature.funds.bottom_sheets.model.UserCardsState
import com.example.finalproject.presentation.stock_feature.funds.model.CreditCard
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserCreditCardsBottomSheetFragment : BottomSheetDialogFragment(){

    interface OnCreditCardClickedListener {
        fun onCreditCardClickedListener(creditCard : CreditCard)
    }

    var onCreditCardClickedListener : OnCreditCardClickedListener? = null

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

    private fun handleCardClicked(card : CreditCard) {
        onCreditCardClickedListener?.onCreditCardClickedListener(creditCard = card)
        dismiss()
    }

    private fun handleErrorMessage(errorMessage : String) {
        Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG).setAction("OK"){}.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}