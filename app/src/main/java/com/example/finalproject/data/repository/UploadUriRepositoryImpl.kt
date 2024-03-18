package com.example.finalproject.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.example.finalproject.data.common.ErrorType
import com.example.finalproject.data.common.Resource
import com.example.finalproject.domain.repository.UploadUriRepository
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.util.UUID
import javax.inject.Inject

class UploadUriRepositoryImpl @Inject constructor(
    private val storageReference: StorageReference,
    private val context: Context
) : UploadUriRepository {
    override suspend fun uploadImage(uri: Uri, uid: String): Flow<Resource<Boolean>> {
        return flow {
            try {
                val bitmap = getBitmapFromUri(uri)
                val fileReference = storageReference.child("images/$uid/${UUID.randomUUID()}.jpg")
                val byteArrayOutputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
                val data = byteArrayOutputStream.toByteArray()

                val uploadTask = fileReference.putBytes(data)
                uploadTask.await()

                emit(Resource.Success(true))
            } catch (e: Exception) {
                emit(Resource.Error(ErrorType.UnknownError(message = e.toString())))
            }
        }
    }

    private suspend fun getBitmapFromUri(uri: Uri): Bitmap {
        return withContext(Dispatchers.IO) {
            val inputStream = context.contentResolver.openInputStream(uri)
            BitmapFactory.decodeStream(inputStream)
        }
    }

    override suspend fun retrieveUploadedImage(uid: String): Flow<Resource<Uri>> {
        return flow {
            try {
                val fileReference = storageReference.child("images/$uid")
                val downloadUrl = fileReference.downloadUrl.await()

                emit(Resource.Success(downloadUrl))
            } catch (e: Exception) {
                emit(Resource.Error(ErrorType.UnknownError(message = e.toString())))
            }
        }
    }
}