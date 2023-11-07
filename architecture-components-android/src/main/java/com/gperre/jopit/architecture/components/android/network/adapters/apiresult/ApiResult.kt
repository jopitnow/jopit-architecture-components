package com.gperre.jopit.architecture.components.android.network.adapters.apiresult

import com.gperre.jopit.architecture.components.android.network.errors.ServiceError

sealed class ApiResult<T> {
    class ERROR<T>(val error: ServiceError): ApiResult<T>()
    class SUCCESS<T>(val result: T?): ApiResult<T>()
}