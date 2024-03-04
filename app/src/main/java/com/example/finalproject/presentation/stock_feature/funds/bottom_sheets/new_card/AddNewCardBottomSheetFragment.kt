package com.example.finalproject.presentation.stock_feature.funds.bottom_sheets.new_card

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.finalproject.databinding.BottomSheetAddNewCardLayoutBinding
import com.example.finalproject.presentation.stock_feature.funds.bottom_sheets.event.AddNewCardEvent
import com.example.finalproject.presentation.stock_feature.funds.bottom_sheets.existing_cards.UserCreditCardsBottomSheetFragmentDirections
import com.example.finalproject.presentation.stock_feature.funds.bottom_sheets.state.NewCardState
import com.example.finalproject.presentation.stock_feature.funds.model.CreditCard
import com.example.finalproject.presentation.util.formatExpirationDate
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddNewCardBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: BottomSheetAddNewCardLayoutBinding? = null
    private val binding get() = _binding!!

    private val addNewCardViewModel : AddNewCardViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = BottomSheetAddNewCardLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindNumbersField()
        bindNewCreditCard()
        bindSuccessFlow()
        bindNavigationFlow()
    }

    private fun bindNewCreditCard() {
        with(binding) {
            btnAddCreditCard.setOnClickListener {
                addNewCardViewModel.onEvent(AddNewCardEvent.AddCreditCard(
                    cardNumber = etCardNumber.text?.chunked(size = 4) ?: emptyList(),
                    expirationDate = formatExpirationDate(etExpiryMonth, etExpiryYear),
                    ccv = etCvv.text.toString()))
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

    private fun handleState(state : NewCardState) {
        with(binding) {
            if(state.isLoading) progressBar.visibility = View.VISIBLE

            state.errorMessage?.let { message -> handleErrorMessage(errorMessage = message) }

            if(state.success) {
                progressBar.visibility = View.GONE
                handleNewCardAdded()
            }
        }
    }

    private fun bindNavigationFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                addNewCardViewModel.navigationFlow.collect { event ->
                    when(event) {
                        is NewCardNavigationFlow.NavigateBack -> handleNavigation(card = event.creditCard)
                    }
                }
            }
        }
    }

    private fun handleNewCardAdded() {
        Snackbar.make(binding.root, "Credit Card Added Successfully!", Snackbar.LENGTH_LONG).show()
    }

    private fun handleNavigation(card : CreditCard) {
        val action = AddNewCardBottomSheetFragmentDirections.actionAddNewCardBottomSheetFragmentToUserFundsFragment(card)
        findNavController().navigate(action)
    }

    private fun handleErrorMessage(errorMessage : String) {
        Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG).setAction("OK"){}.show()
    }

    private fun bindNumbersField() {
        binding.etCardNumber.addTextChangedListener(object : TextWatcher {
            private var isFormatting = false
            private var backspacingFlag = false
            private var cursorPosition = 0

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                cursorPosition = binding.etCardNumber.selectionStart
                backspacingFlag = count > after
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (!isFormatting) {
                    isFormatting = true
                    val inputLength = s!!.length
                    val formattedString = s.toString().replace(" ", "").chunked(4).joinToString(" ")
                    s.replace(0, s.length, formattedString)

                    if (backspacingFlag && cursorPosition != inputLength) {
                        if (cursorPosition > formattedString.length) cursorPosition = formattedString.length
                        binding.etCardNumber.setSelection(cursorPosition)
                    } else if (cursorPosition < formattedString.length) {
                        binding.etCardNumber.setSelection(cursorPosition + 1)
                    }

                    isFormatting = false
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}