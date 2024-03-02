package com.example.finalproject.presentation.stock_feature.credit_card

import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentAddCreditCardLayoutBinding
import com.example.finalproject.presentation.base.BaseFragment
import com.example.finalproject.presentation.stock_feature.credit_card.event.AddCreditCardEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddCreditCardFragment : BaseFragment<FragmentAddCreditCardLayoutBinding>(FragmentAddCreditCardLayoutBinding::inflate) {

    private val creditCardViewModel: AddCreditCardViewModel by viewModels()

    override fun bind() {
        bindNumbersField()
    }

    override fun bindViewActionListeners() {
        bindBackBtn()
        bindAddCreditCardBtn()
    }

    override fun bindObservers() {
        bindNavigationFlow()
    }

    private fun bindBackBtn() {
        with(binding) {
            btnBack.setOnClickListener {
                creditCardViewModel.onEvent(AddCreditCardEvent.BackButtonPressed)
            }
        }
    }

    private fun bindAddCreditCardBtn() {
        with(binding) {
            btnAddCreditCard.setOnClickListener {
                creditCardViewModel.onEvent(
                    AddCreditCardEvent.AddCreditCard(
                        cardNumber = "4344222233334444".chunked(4),
                        expirationDate = "12/34",
                        ccv = "432"
                    )
                )
            }
        }
    }

    private fun bindNavigationFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                creditCardViewModel.navigationFlow.collect { event ->
                    when (event) {
                        is AddCreditCardNavigationEvent.NavigateBack -> navigateBack()
                    }
                }
            }
        }
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

    private fun navigateBack() {
//        findNavController().navigate(R.id.action_addCreditCardFragment_to_userFundsFragment)
    }
}