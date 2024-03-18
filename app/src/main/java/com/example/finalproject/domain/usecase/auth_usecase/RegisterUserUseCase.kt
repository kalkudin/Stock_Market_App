package com.example.finalproject.domain.usecase.auth_usecase

import com.example.finalproject.data.common.ErrorType
import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.model.user.GetUser
import com.example.finalproject.domain.repository.auth.register.RegisterRepository
import com.example.finalproject.domain.util.AuthenticationUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val registerRepository: RegisterRepository,
    private val authenticationUtil: AuthenticationUtil
) {
    operator fun invoke(email: String, password: String, repeatPassword : String, firstName : String, lastName : String): Flow<Resource<String>> {

        if (!authenticationUtil.areFieldsNotEmpty(email, password, repeatPassword, firstName, lastName)) {
            return flowOf(Resource.Error(ErrorType.FieldsEmpty))
        }

        if (!authenticationUtil.isValidEmail(email)) {
            return flowOf(Resource.Error(ErrorType.InvalidEmail))
        }

        if (!authenticationUtil.isValidPassword(password)) {
            return flowOf(Resource.Error(ErrorType.PasswordCriteriaNotMet))
        }

        if (!authenticationUtil.doPasswordsMatch(password, repeatPassword)) {
            return flowOf(Resource.Error(ErrorType.PasswordsNotMatching))
        }

        val user = GetUser(email = email, password = password)
        return registerRepository.registerUser(user = user)
    }
}