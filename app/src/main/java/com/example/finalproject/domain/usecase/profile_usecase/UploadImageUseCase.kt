package com.example.finalproject.domain.usecase.profile_usecase

import android.net.Uri
import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.repository.UploadUriRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UploadImageUseCase @Inject constructor(private val uploadUriRepository : UploadUriRepository) {
    suspend operator fun invoke(uri : Uri, uid : String) : Flow<Resource<Boolean>> {
        return uploadUriRepository.uploadImage(uri = uri, uid = uid)
    }
}