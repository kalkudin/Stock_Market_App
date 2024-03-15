package com.example.finalproject.presentation.bottom_sheets.new_card

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.R
import com.example.finalproject.databinding.BottomSheetAddNewCardLayoutBinding
import com.example.finalproject.presentation.adapters.bottom_sheet.CardTypeRecyclerViewAdapter
import com.example.finalproject.presentation.base.BaseBottomSheet
import com.example.finalproject.presentation.bottom_sheets.event.AddNewCardEvent
import com.example.finalproject.presentation.bottom_sheets.state.NewCardState
import com.example.finalproject.presentation.model.bottom_sheets.NewCardType
import com.example.finalproject.presentation.model.funds.CreditCard
import com.example.finalproject.presentation.util.CreditCardNumberFormattingTextWatcher
import com.example.finalproject.presentation.util.formatExpirationDate
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Locale

@AndroidEntryPoint
class AddNewCardBottomSheetFragment : BaseBottomSheet<BottomSheetAddNewCardLayoutBinding>(BottomSheetAddNewCardLayoutBinding::inflate){

    private val addNewCardViewModel: AddNewCardViewModel by viewModels()

    private lateinit var cardTypeRecyclerViewAdapter : CardTypeRecyclerViewAdapter

    private var cardType : CreditCard.CardType = CreditCard.CardType.UNKNOWN

    override fun bind() {
        bindCardTypeAdapter()
        bindCardNumber()
    }

    override fun bindViewActionListeners() {
        bindNewCreditCard()
    }

    override fun bindObserves() {
        bindSuccessFlow()
        bindNavigationFlow()
    }

    private fun bindCardNumber() {
        val etCardNumber = binding.etCardNumber
        etCardNumber.addTextChangedListener(CreditCardNumberFormattingTextWatcher())
    }

    private fun bindCardTypeAdapter() {
        with(binding) {
            cardTypeRecyclerViewAdapter = CardTypeRecyclerViewAdapter { type ->
                handleCardTypeSelected(type)
            }
            rvCardType.apply {
                adapter = cardTypeRecyclerViewAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }

    private fun bindNewCreditCard() {
        with(binding) {
            btnAddCreditCard.setOnClickListener {
                addNewCardViewModel.onEvent(
                    AddNewCardEvent.AddCreditCard(
                        cardNumber = etCardNumber.text?.chunked(size = 4) ?: emptyList(),
                        expirationDate = formatExpirationDate(etExpiryMonth, etExpiryYear),
                        ccv = etCcv.text.toString(),
                        cardType = cardType.toString().lowercase()
                    )
                )
            }
        }
    }

    private fun bindSuccessFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                addNewCardViewModel.successFlow.collect { state ->
                    handleState(state = state)
                }
            }
        }
    }

    private fun handleState(state: NewCardState) {
        with(binding) {
            if (state.isLoading) progressBar.visibility = View.VISIBLE

            state.errorMessage?.let { message ->
                handleErrorMessage(errorMessage = message)
                addNewCardViewModel.onEvent(event = AddNewCardEvent.ResetErrorMessage)
            }

            state.cardTypeList?.let { list ->
                cardTypeRecyclerViewAdapter.submitList(list)
            }

            if (state.success) {
                progressBar.visibility = View.GONE

                addNewCardViewModel.onEvent(AddNewCardEvent.NavigateToFundsFragment(
                    cardNumber = etCardNumber.text?.chunked(size = 4) ?: emptyList(),
                    expirationDate = formatExpirationDate(etExpiryMonth, etExpiryYear),
                    ccv = etCcv.text.toString(),
                    cardType = cardType
                ))
            }
        }
    }

    private fun bindNavigationFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                addNewCardViewModel.navigationFlow.collect { event ->
                    when (event) {
                        is NewCardNavigationFlow.NavigateBack -> handleNavigation(card = event.creditCard)
                    }
                }
            }
        }
    }

    private fun handleNavigation(card: CreditCard) {
        val action = AddNewCardBottomSheetFragmentDirections.actionAddNewCardBottomSheetFragmentToUserFundsFragment(card)
        findNavController().navigate(action)
    }

    private fun handleErrorMessage(errorMessage: String) {
        Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG).setAction("OK") {}.show()
    }

    private fun handleCardTypeSelected(card : NewCardType) {
        with(binding) {
            when (card.type) {
                CreditCard.CardType.VISA -> {
                    cardType = CreditCard.CardType.VISA
                    cardContainer.setImageResource(R.drawable.style_card_visa)

                    ivCardType.setImageResource(R.drawable.ic_visa_icon)
                }
                CreditCard.CardType.MASTER_CARD -> {
                    cardType = CreditCard.CardType.MASTER_CARD
                    cardContainer.setImageResource(R.drawable.style_card_mastercard)
                    ivCardType.setImageResource(R.drawable.ic_master_card_icon)
                }
                CreditCard.CardType.UNKNOWN -> {
                    cardType = CreditCard.CardType.UNKNOWN
                    cardContainer.setImageResource(R.drawable.style_card_type_unknown)
                    ivCardType.setImageResource(R.drawable.ic_blank_card_icon)
                }
            }
        }
    }
}