package com.gperre.jopit.architecture.components.android.network.interceptors.header

import androidx.annotation.RestrictTo
import okhttp3.Request
import javax.inject.Inject

@RestrictTo(RestrictTo.Scope.LIBRARY)
class WebHeaderAttaching @Inject constructor(
    private val paramsHeaderInterceptor: ParamsHeaderInterceptor
) {

    fun attachHttpInterceptors(builder: Request.Builder?) {
        paramsHeaderInterceptor.getMap().forEach { entry: Map.Entry<String, String> ->
            builder?.header(entry.key, entry.value)
        }
    }
}
