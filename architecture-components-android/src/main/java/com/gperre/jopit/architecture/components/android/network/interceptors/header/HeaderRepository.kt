package com.gperre.jopit.architecture.components.android.network.interceptors.header

import com.gperre.jopit.architecture.components.android.extensions.empty
import com.gperre.jopit.architecture.components.android.network.interceptors.models.InterceptorData.BEARER_AUTHORIZATION
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HeaderRepository @Inject constructor() {

    private lateinit var _authorizationToken: String

    fun getAuthorizationToken(): String {
        return if (::_authorizationToken.isInitialized && _authorizationToken.isNotBlank()) {
            "$BEARER_AUTHORIZATION $_authorizationToken"
        } else {
            String.empty()
        }
    }

    fun setAuthorizationToken(token: String) {
        _authorizationToken = token
    }

    fun clearAuthorizationToken() {
        _authorizationToken = String.empty()
    }
}
