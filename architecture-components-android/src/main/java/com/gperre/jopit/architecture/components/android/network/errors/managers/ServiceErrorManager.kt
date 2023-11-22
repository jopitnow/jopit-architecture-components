package com.gperre.jopit.architecture.components.android.network.errors.managers

import com.google.gson.Gson
import com.gperre.jopit.architecture.components.android.network.errors.KnowErrors
import com.gperre.jopit.architecture.components.android.network.errors.KnowErrors.HTTP_TOKEN_ERROR
import com.gperre.jopit.architecture.components.android.network.errors.ServiceError
import com.gperre.jopit.architecture.components.android.network.errors.dto.WSError
import com.gperre.jopit.architecture.components.android.network.exceptions.NoNetworkException
import java.net.HttpURLConnection
import retrofit2.HttpException

class ServiceErrorManager {

    fun process(throwable: Throwable) = when (throwable) {
        is HttpException -> handleServiceError(throwable)
        else -> handleGeneralExceptions(throwable)
    }

    private fun handleServiceError(httpException: HttpException): ServiceError {
        with(httpException) {
            var remoteCode = code()
            val jsonErrorBody = httpException.response()?.errorBody()?.string() ?: ""
            val internalCode = when (remoteCode) {
                HttpURLConnection.HTTP_BAD_REQUEST -> KnowErrors.HTTP_BAD_REQUEST
                HttpURLConnection.HTTP_UNAUTHORIZED -> {
                    remoteCode = KnowErrors.HTTP_UNAUTHORIZED
                    getInternalCodeForUnauthorizedException(jsonErrorBody, httpException)
                }
                HttpURLConnection.HTTP_NOT_FOUND -> KnowErrors.HTTP_NOT_FOUND
                HttpURLConnection.HTTP_INTERNAL_ERROR -> KnowErrors.HTTP_INTERNAL_ERROR
                HttpURLConnection.HTTP_UNAVAILABLE -> KnowErrors.HTTP_UNAVAILABLE
                else -> KnowErrors.APP_UNHANDLED_ERROR
            }
            return ServiceError(internalCode, remoteCode, message, jsonErrorBody)
        }
    }

    private fun handleGeneralExceptions(throwable: Throwable): ServiceError {
        val errorCode = when (throwable) {
            is NoNetworkException -> KnowErrors.APP_NO_NETWORK
            else -> KnowErrors.APP_UNAVAILABLE_CONNECTION
        }
        return ServiceError(errorCode,0, throwable.message)
    }

    private fun getInternalCodeForUnauthorizedException(
        errorBody: String,
        httpException: HttpException
    ): Int {
        return try {
            val wsError = Gson().fromJson(errorBody, WSError::class.java)
            when (wsError.messages.getOrNull(0)) {
                UNAUTHORIZED_MESSAGE -> HTTP_TOKEN_ERROR
                else -> httpException.code()
            }
        } catch (throwable: Throwable) {
            httpException.code()
        }
    }

    companion object {
        const val UNAUTHORIZED_MESSAGE = "Can't get a valid access token."
    }
}
