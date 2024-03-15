package com.example.finalproject.presentation.screen.funds

import android.os.Build
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentFundsLayoutBinding
import com.example.finalproject.presentation.base.BaseFragment
import com.example.finalproject.presentation.event.funds.UserFundsEvent
import com.example.finalproject.presentation.mapper.funds.maskAndFormatCardNumber
import com.example.finalproject.presentation.model.funds.CreditCard
import com.example.finalproject.presentation.state.funds.FundsState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserFundsFragment : BaseFragment<FragmentFundsLayoutBinding>(FragmentFundsLayoutBinding::inflate){

    private val fundsViewModel : UserFundsViewModel by viewModels()

    private var userCardNumber : String = ""

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun bind() {
        bindArgs()
        bindUserName()
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

    private fun bindUserName() {
        fundsViewModel.onEvent(UserFundsEvent.GetUserName)
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

            state.userInitials?.let { initials ->
                bindUserInitials(text = initials)
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

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun bindArgs() {
        val creditCard = arguments?.getParcelable("creditCard", CreditCard::class.java)
        Log.d("FundsFragment", creditCard.toString())

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

            when(type) {
                CreditCard.CardType.VISA -> {
                    cardContainer.setImageResource(R.drawable.style_card_visa)
                    backgroundScreen.setImageResource(R.drawable.style_card_visa)
                    ivCardType.setImageResource(R.drawable.ic_visa_icon)
                }
                CreditCard.CardType.MASTER_CARD -> {
                    cardContainer.setImageResource(R.drawable.style_card_mastercard)
                    backgroundScreen.setImageResource(R.drawable.style_card_mastercard)
                    ivCardType.setImageResource(R.drawable.ic_master_card_icon)
                }
                CreditCard.CardType.UNKNOWN -> {
                    cardContainer.setImageResource(R.drawable.style_card_type_unknown)
                    backgroundScreen.setImageResource(R.drawable.style_card_type_unknown)
                    ivCardType.setImageResource(R.drawable.ic_blank_card_icon)
                }
            }
        }
    }

    private fun bindUserInitials(text : String) {
        with(binding) {
            tvUserInitials.text = text
        }
    }

    private fun handleFundsAdded(amount : String) {
        Snackbar.make(binding.root, "$amount$ has successfully been added to you account", Snackbar.LENGTH_LONG).show()
    }
}