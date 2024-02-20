package com.example.finalproject.data.common

import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

class HandleResponse {
    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>) = flow {
        emit(Resource.Loading(loading = true))
        try {
            val response = call()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                emit(Resource.Success(data = body))
            } else {
                val errorMessage = response.errorBody()?.string() ?: "Error Occurred"
                emit(Resource.Error(ErrorType.UnknownError(errorMessage)))
            }
        } catch (e: SocketTimeoutException) {
            emit(Resource.Error(ErrorType.SocketTimeout))
        } catch (e: UnknownHostException) {
            emit(Resource.Error(ErrorType.UnknownHost))
        } catch (e: SSLHandshakeException) {
            emit(Resource.Error(ErrorType.SSLHandshake))
        } catch (e: HttpException) {
            emit(Resource.Error(ErrorType.Http))
        } catch (e: IOException) {
            emit(Resource.Error(ErrorType.IO))
        } catch (e: Exception) {
            emit(Resource.Error(ErrorType.UnknownError(e.message ?: "Unknown Error Occurred")))
        }
        emit(Resource.Loading(loading = false))
    }
}