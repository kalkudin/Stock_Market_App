package com.example.finalproject.domain.usecase.auth_usecase

import com.example.finalproject.data.common.ErrorType
import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.model.User
import com.example.finalproject.domain.repository.LoginRepository
import com.example.finalproject.domain.util.AuthenticationUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val loginRepository: LoginRepository,
    private val authenticationUtil: AuthenticationUtil
) {
    operator fun invoke(email : String, password : String) : Flow<Resource<String>> {
        if (!authenticationUtil.areFieldsNotEmpty(email, password)) {
            return flowOf(Resource.Error(ErrorType.FieldsEmpty))
        }

        if (!authenticationUtil.isValidEmail(email)) {
            return flowOf(Resource.Error(ErrorType.InvalidEmail))
        }

        val user = User(email = email, password = password)
        return loginRepository.loginUser(user = user)
    }
}