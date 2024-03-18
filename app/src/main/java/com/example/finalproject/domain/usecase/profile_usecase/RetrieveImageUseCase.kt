package com.example.finalproject.domain.usecase.profile_usecase

import android.net.Uri
import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.repository.UploadUriRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RetrieveImageUseCase @Inject constructor(private val uploadUriRepository: UploadUriRepository) {
    suspend operator fun invoke(uid : String) : Flow<Resource<Uri>> {
        return uploadUriRepository.retrieveUploadedImage(uid = uid)
    }
}