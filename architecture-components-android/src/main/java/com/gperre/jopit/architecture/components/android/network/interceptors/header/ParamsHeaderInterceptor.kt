package com.gperre.jopit.architecture.components.android.network.interceptors.header

import androidx.annotation.RestrictTo
import com.gperre.jopit.architecture.components.android.network.interceptors.models.InterceptorData.AUTHORIZATION
import javax.inject.Inject

@RestrictTo(RestrictTo.Scope.LIBRARY)
class ParamsHeaderInterceptor @Inject constructor(
    private val repository: HeaderRepository
) {

    fun getMap(): Map<String, String> {
        return mutableMapOf<String, String>().apply {
            put(AUTHORIZATION, repository.getAuthorizationToken())
        }
    }
}
