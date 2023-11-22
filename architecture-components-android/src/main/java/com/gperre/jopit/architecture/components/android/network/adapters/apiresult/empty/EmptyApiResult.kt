package com.gperre.jopit.architecture.components.android.network.adapters.apiresult.empty

import com.gperre.jopit.architecture.components.android.network.errors.ServiceError

sealed class EmptyApiResult {
    class ERROR(val error: ServiceError): EmptyApiResult()
    data object SUCCESS : EmptyApiResult()
}
