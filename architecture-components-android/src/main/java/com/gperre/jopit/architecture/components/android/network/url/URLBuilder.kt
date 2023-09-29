package com.gperre.jopit.architecture.components.android.network.url

import androidx.annotation.RestrictTo
import javax.inject.Inject

@RestrictTo(RestrictTo.Scope.LIBRARY)
class URLBuilder @Inject constructor() {

    fun getBaseURL() = "https://api.jopit.com.ar/"

    fun getMockURL() = "https://private-f0a573-jopittest.apiary-mock.com/"

    fun getBackendRawURL() = "http://191.101.15.182/"
}
