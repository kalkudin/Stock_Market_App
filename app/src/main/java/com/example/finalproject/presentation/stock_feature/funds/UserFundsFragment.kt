package com.example.finalproject.presentation.stock_feature.funds

import androidx.fragment.app.viewModels
import com.example.finalproject.databinding.FragmentFundsLayoutBinding
import com.example.finalproject.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFundsFragment : BaseFragment<FragmentFundsLayoutBinding>(FragmentFundsLayoutBinding::inflate) {

    private val fundsViewModel : UserFundsViewModel by viewModels()

    override fun bind() {

    }

    override fun bindViewActionListeners() {

    }

    override fun bindObservers() {

    }
}