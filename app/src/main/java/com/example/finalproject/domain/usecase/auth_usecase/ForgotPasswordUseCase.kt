package com.example.finalproject.domain.usecase.auth_usecase

import com.example.finalproject.data.common.ErrorType
import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.repository.ResetPasswordRepository
import com.example.finalproject.domain.util.AuthenticationUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class ForgotPasswordUseCase @Inject constructor(
    private val resetPasswordRepository: ResetPasswordRepository,
    private val authenticationUtil: AuthenticationUtil
) {
    operator fun invoke(email: String): Flow<Resource<Boolean>> {

        if (!authenticationUtil.areFieldsNotEmpty(email)) {
            return flowOf(Resource.Error(ErrorType.FieldsEmpty))
        }

        if (!authenticationUtil.isValidEmail(email)) {
            return flowOf(Resource.Error(ErrorType.InvalidEmail))
        }

        return resetPasswordRepository.resetPassword(email)
    }
}