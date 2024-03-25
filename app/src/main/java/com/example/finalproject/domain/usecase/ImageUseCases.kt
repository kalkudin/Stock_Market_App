package com.example.finalproject.domain.usecase

import com.example.finalproject.domain.usecase.profile_usecase.RetrieveImageUseCase
import com.example.finalproject.domain.usecase.profile_usecase.UploadImageUseCase
import javax.inject.Inject

data class ImageUseCases @Inject constructor(
    val retrieveImageUseCase: RetrieveImageUseCase,
    val uploadImageUseCase: UploadImageUseCase
)