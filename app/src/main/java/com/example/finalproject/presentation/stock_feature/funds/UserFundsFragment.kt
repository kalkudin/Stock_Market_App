package com.example.finalproject.presentation.stock_feature.funds

import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentFundsLayoutBinding
import com.example.finalproject.presentation.base.BaseFragment
import com.example.finalproject.presentation.stock_feature.funds.bottom_sheets.existing_cards.UserCreditCardsBottomSheetFragment
import com.example.finalproject.presentation.stock_feature.funds.bottom_sheets.new_card.AddNewCardBottomSheetFragment
import com.example.finalproject.presentation.stock_feature.funds.event.UserFundsEvent
import com.example.finalproject.presentation.stock_feature.funds.mapper.maskAndFormatCardNumber
import com.example.finalproject.presentation.stock_feature.funds.model.CreditCard
import com.example.finalproject.presentation.stock_feature.funds.state.FundsState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserFundsFragment : BaseFragment<FragmentFundsLayoutBinding>(FragmentFundsLayoutBinding::inflate),
    AddNewCardBottomSheetFragment.OnNewCardAddedListener,
    UserCreditCardsBottomSheetFragment.OnCreditCardClickedListener{

    private val fundsViewModel : UserFundsViewModel by viewModels()

    private var userCardNumber : String = ""

    override fun bind() {
    }

    override fun bindViewActionListeners() {
        bindFunds()
        bindBackBtn()
        bindAddCreditCardBottomSheet()
        bindGetCreditCardsBottomSheet()
    }

    override fun bindObservers() {
        bindNavigationFlow()
        bindSuccessFlow()
    }

    private fun bindBackBtn() {
        with(binding) {
            btnBack.setOnClickListener {
                fundsViewModel.onEvent(UserFundsEvent.BackButtonPressed)
            }
        }
    }

    private fun bindFunds() {
        with(binding) {
            btnAddFunds.setOnClickListener {
                fundsViewModel.onEvent(UserFundsEvent.AddFunds(cardNumber = userCardNumber, amount = etAddFunds.text.toString()))
            }
        }
    }

    private fun bindAddCreditCardBottomSheet() {
        with(binding) {
            btnNewCreditCard.setOnClickListener {
                val bottomSheetFragment = AddNewCardBottomSheetFragment().apply {
                    onNewCardAddedListener = this@UserFundsFragment
                }
                bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
            }
        }
    }

    private fun bindGetCreditCardsBottomSheet() {
        with(binding) {
            btnGetAllCards.setOnClickListener {
                val bottomSheetFragment = UserCreditCardsBottomSheetFragment().apply {
                    onCreditCardClickedListener = this@UserFundsFragment
                }
                bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
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

    private fun bindSuccessFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                fundsViewModel.successFlow.collect {state ->
                    handleState(state)
                }
            }
        }
    }

    private fun handleState(state : FundsState) {
        with(binding) {
            if(state.isLoading) progressBar.visibility = View.VISIBLE

            state.errorMessage?.let { errorMessage ->
                progressBar.visibility = View.GONE
                handleErrorMessage(errorMessage)
                resetFlow()
            }

            if(state.success) {
                progressBar.visibility = View.GONE
                handleFundsAdded(etAddFunds.text.toString())
            }
        }
    }

    private fun handleErrorMessage(errorMessage : String) {
        Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG).setAction("OK"){}.show()
    }

    private fun navigateBack() {
        findNavController().navigate(R.id.action_userFundsFragment_to_stockHomeFragment)
    }

    private fun resetFlow() {
        fundsViewModel.onEvent(UserFundsEvent.ResetFlow)
    }

    override fun onNewCardAdded(cardNumber: String, expirationDate: String, ccv: String, type: CreditCard.CardType) {
        Log.d("HomeFragment", "Selected Account: $cardNumber, Number: $expirationDate")
        userCardNumber = cardNumber
        handleNewCardAdded()
        bindCreditCardDisplayInformation(
            cardNumber = cardNumber,
            expirationDate = expirationDate,
            ccv = ccv,
            type = type)
    }

    override fun onCreditCardClickedListener(creditCard: CreditCard) {
        Log.d("HomeFragment", creditCard.cardNumber)
        userCardNumber = creditCard.cardNumber
        bindCreditCardDisplayInformation(
            cardNumber = creditCard.cardNumber,
            expirationDate = creditCard.expirationDate,
            ccv = creditCard.ccv,
            type = creditCard.cardType
        )
    }

    private fun bindCreditCardDisplayInformation(cardNumber : String, expirationDate: String, ccv: String, type : CreditCard.CardType) {
        with(binding) {
            tvCardNumber.text = maskAndFormatCardNumber(cardNumber)
            tvExpiryDate.text = expirationDate
            tvCcv.text = ccv
        }
    }

    private fun handleNewCardAdded() {
        Snackbar.make(binding.root, "Credit Card Added Successfully!", Snackbar.LENGTH_LONG).show()
    }

    private fun handleFundsAdded(amount : String) {
        Snackbar.make(binding.root, "$amount has successfully been added to you account", Snackbar.LENGTH_LONG).show()
    }
}