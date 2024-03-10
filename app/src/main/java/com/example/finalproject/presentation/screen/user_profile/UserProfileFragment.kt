package com.example.finalproject.presentation.screen.user_profile

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentProfileLayoutBinding
import com.example.finalproject.presentation.adapters.profile.CreditCardViewPagerAdapter
import com.example.finalproject.presentation.adapters.profile.ProfileFavoriteStocksRecyclerAdapter
import com.example.finalproject.presentation.adapters.profile.TransactionRecyclerAdapter
import com.example.finalproject.presentation.base.BaseFragment
import com.example.finalproject.presentation.event.profile.UserProfileEvent
import com.example.finalproject.presentation.extension.hideView
import com.example.finalproject.presentation.extension.showView
import com.example.finalproject.presentation.model.funds.CreditCard
import com.example.finalproject.presentation.state.profile.ProfileState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserProfileFragment : BaseFragment<FragmentProfileLayoutBinding>(FragmentProfileLayoutBinding::inflate) {

    private val userProfileViewModel : UserProfileViewModel by viewModels()

    private lateinit var creditCardViewPagerAdapter : CreditCardViewPagerAdapter

    private lateinit var transactionRecyclerAdapter : TransactionRecyclerAdapter

    private lateinit var favoriteStocksRecyclerAdapter : ProfileFavoriteStocksRecyclerAdapter

    override fun bind() {
        bindProfileData()
        bindCardsAdapter()
        bindFavoritesAdapter()
        bindTransactionsAdapter()
    }

    override fun bindViewActionListeners() {
        bindTransactionDetailsBtn()
        bindFavoriteStocksBtn()
    }

    override fun bindObservers() {
        bindNavigationFlow()
        bindProfileState()
    }

    private fun bindTransactionDetailsBtn(){
        with(binding) {
            btnViewMoreTransactions.setOnClickListener {
                userProfileViewModel.onEvent(UserProfileEvent.ViewTradingHistory)
            }
        }
    }

    private fun bindFavoriteStocksBtn() {
        with(binding) {
            btnViewFavoriteStocks.setOnClickListener {
                userProfileViewModel.onEvent(UserProfileEvent.NavigateToFavorites)
            }
        }
    }

    private fun bindProfileData() {
        userProfileViewModel.onEvent(UserProfileEvent.RetrieveProfileData)
    }

    private fun bindCardsAdapter() {
        with(binding) {
            creditCardViewPagerAdapter = CreditCardViewPagerAdapter(
                onDeleteCardClick = { creditCard ->
                    handleCreditCardDelete(creditCard)
                }
            )
            vpUserCards.adapter = creditCardViewPagerAdapter
        }
    }

    private fun bindTransactionsAdapter() {
        with(binding) {
            transactionRecyclerAdapter = TransactionRecyclerAdapter()

            rvTransactions.adapter= transactionRecyclerAdapter
        }
    }

    private fun bindFavoritesAdapter() {
        with(binding) {
            favoriteStocksRecyclerAdapter = ProfileFavoriteStocksRecyclerAdapter()
            rvFavoriteStocks.adapter =favoriteStocksRecyclerAdapter
            rvFavoriteStocks.layoutManager = LinearLayoutManager(rvFavoriteStocks.context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun bindProfileState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                userProfileViewModel.profileState.collect { state ->
                    handleState(state = state)
                }
            }
        }
    }

    private fun handleState(state : ProfileState) {
        with(binding) {
            if (state.isLoading) {
                showView(progressBar)
                hideView(transactionContainer)
                hideView(cardContainer)
                hideView(categoryFavoriteStockContainer)
            }

            state.errorMessage?.let { errorMessage ->
                hideView(progressBar)
                handleErrorMessage(errorMessage = errorMessage) }

            if(!state.isLoading) {
                hideView(progressBar)

                state.cardList?.let { list ->
                    showView(cardContainer)
                    creditCardViewPagerAdapter.submitList(list)
                }

                state.transactionList?.let { list ->
                    showView(transactionContainer)
                    transactionRecyclerAdapter.submitList(list)
                }

                state.favoriteStockList?.let { list ->
                    showView(categoryFavoriteStockContainer)
                    favoriteStocksRecyclerAdapter.submitList(list)
                }
            }
        }
    }

    private fun bindNavigationFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                userProfileViewModel.navigationFlow.collect { event ->
                    handleNavigationEvent(event = event)
                }
            }
        }
    }

    private fun handleNavigationEvent(event : UserProfileNavigationEvent) {
        when(event) {
            is UserProfileNavigationEvent.NavigateToAddFunds -> {

            }
            is UserProfileNavigationEvent.NavigateToFavorites -> {
                openFavorites()
            }
            is UserProfileNavigationEvent.OpenCreditCardManager -> {

            }
            is UserProfileNavigationEvent.OpenTradingHistory -> {
                openTradingHistory()
            }
            is UserProfileNavigationEvent.UpdateProfile -> {

            }
        }
    }

    private fun handleCreditCardDelete(creditCard: CreditCard) {
        userProfileViewModel.onEvent(UserProfileEvent.RemoveCard(creditCard = creditCard))
    }

    private fun openTradingHistory() {
        findNavController().navigate(R.id.action_userProfileFragment_to_transactionFragment)
    }

    private fun openFavorites() {
        findNavController().navigate(R.id.action_userProfileFragment_to_watchlistedStocksFragment)
    }

    private fun handleErrorMessage(errorMessage : String) {
        Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG).setAction("OK"){}.show()
    }
}