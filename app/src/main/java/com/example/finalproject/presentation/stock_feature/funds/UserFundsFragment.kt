package com.example.finalproject.presentation.stock_feature.funds

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentFundsLayoutBinding
import com.example.finalproject.presentation.base.BaseFragment
import com.example.finalproject.presentation.stock_feature.funds.event.UserFundsEvent
import com.example.finalproject.presentation.stock_feature.funds.mapper.maskAndFormatCardNumber
import com.example.finalproject.presentation.stock_feature.funds.model.CreditCard
import com.example.finalproject.presentation.stock_feature.funds.state.FundsState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserFundsFragment : BaseFragment<FragmentFundsLayoutBinding>(FragmentFundsLayoutBinding::inflate){

    private val fundsViewModel : UserFundsViewModel by viewModels()

    private var userCardNumber : String = ""

//    private val args: UserFundsFragmentArgs by navArgs()

    override fun bind() {
        bindArgs()
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
                fundsViewModel.onEvent(UserFundsEvent.OpenNewCardBottomSheet)
            }
        }
    }

    private fun bindGetCreditCardsBottomSheet() {
        with(binding) {
            btnGetAllCards.setOnClickListener {
                fundsViewModel.onEvent(UserFundsEvent.OpenCardsBottomSheet)
            }
        }
    }

    private fun bindNavigationFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                fundsViewModel.navigationFlow.collect { event ->
                    when(event) {
                        is FundsNavigationEvent.NavigateBack -> navigateBack()
                        is FundsNavigationEvent.OpenCardsBottomSheet -> openCardsBottomSheet()
                        is FundsNavigationEvent.OpenNewCardBottomSheet -> openNewCardBottomSheet()
                    }
                }
            }
        }
    }

    private fun openNewCardBottomSheet() {
        findNavController().navigate(R.id.action_userFundsFragment_to_addNewCardBottomSheetFragment)
    }

    private fun openCardsBottomSheet() {
        findNavController().navigate(R.id.action_userFundsFragment_to_userCreditCardsBottomSheetFragment)
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

    private fun bindArgs() {
        val creditCard = arguments?.getParcelable("creditCard", CreditCard::class.java)

        creditCard?.let {
            userCardNumber = creditCard.cardNumber

            bindCreditCardDisplayInformation(
                cardNumber = creditCard.cardNumber,
                expirationDate = creditCard.expirationDate,
                ccv = creditCard.ccv,
                type = creditCard.cardType
            )
        }
    }

    private fun bindCreditCardDisplayInformation(cardNumber : String, expirationDate: String, ccv: String, type : CreditCard.CardType) {
        with(binding) {
            tvCardNumber.text = maskAndFormatCardNumber(cardNumber)
            tvExpiryDate.text = expirationDate
            tvCcv.text = ccv
        }
    }

    private fun handleFundsAdded(amount : String) {
        Snackbar.make(binding.root, "$amount has successfully been added to you account", Snackbar.LENGTH_LONG).show()
    }
}