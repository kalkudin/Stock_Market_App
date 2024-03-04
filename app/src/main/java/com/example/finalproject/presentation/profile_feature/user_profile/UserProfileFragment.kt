package com.example.finalproject.presentation.profile_feature.user_profile

import androidx.fragment.app.viewModels
import com.example.finalproject.databinding.FragmentProfileLayoutBinding
import com.example.finalproject.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserProfileFragment : BaseFragment<FragmentProfileLayoutBinding>(FragmentProfileLayoutBinding::inflate) {

    private val userProfileViewModel : UserProfileViewModel by viewModels()

    override fun bind() {

    }

    override fun bindViewActionListeners() {

    }

    override fun bindObservers() {

    }
}