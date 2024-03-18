package com.example.finalproject.domain.repository.firestore.image_upload

import android.net.Uri
import com.example.finalproject.data.common.Resource
import kotlinx.coroutines.flow.Flow

interface UploadUriRepository {
    suspend fun uploadImage(uri : Uri, uid : String) : Flow<Resource<Boolean>>
    suspend fun retrieveUploadedImage(uid : String) : Flow<Resource<Uri>>
}