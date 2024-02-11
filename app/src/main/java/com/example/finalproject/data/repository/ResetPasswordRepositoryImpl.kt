package com.example.finalproject.data.repository

import com.example.finalproject.data.common.HandlePasswordReset
import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.repository.ResetPasswordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ResetPasswordRepositoryImpl @Inject constructor(
    private val handlePasswordReset: HandlePasswordReset
) : ResetPasswordRepository {
    override fun resetPassword(email: String): Flow<Resource<Boolean>> {
        return handlePasswordReset.resetPassword(email)
    }
}