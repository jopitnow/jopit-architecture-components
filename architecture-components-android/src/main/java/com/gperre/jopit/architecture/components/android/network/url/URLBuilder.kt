package com.gperre.jopit.architecture.components.android.network.url

import androidx.annotation.RestrictTo
import javax.inject.Inject

@RestrictTo(RestrictTo.Scope.LIBRARY)
class URLBuilder @Inject constructor() {

    fun getBaseURL() = "https://api.jopit.com.ar/"
}
