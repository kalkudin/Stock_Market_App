package com.example.finalproject.domain.usecase.auth_usecase

import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.model.user_initials.GetUserInitials
import com.example.finalproject.domain.repository.firestore.user_initials.UserInitialsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserInitialsUseCase @Inject constructor(private val userInitialsRepository: UserInitialsRepository) {
    suspend operator fun invoke(uid: String): Flow<Resource<GetUserInitials>> {
        return userInitialsRepository.getUserInitials(uid = uid)
    }
}