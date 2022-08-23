package com.gperre.jopit.architecture.components.android.network.interceptors.header

import androidx.annotation.RestrictTo
import com.gperre.jopit.architecture.components.android.network.interceptors.models.InterceptorData.AUTHORIZATION
import javax.inject.Inject

@RestrictTo(RestrictTo.Scope.LIBRARY)
class ParamsHeaderInterceptor @Inject constructor() {

    fun getMap(): Map<String, String> {
        return mutableMapOf<String, String>().apply {
            // TODO: Traer el token aca para mandarlo en el header
            put(AUTHORIZATION, "token")
        }
    }
}
