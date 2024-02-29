package com.gperre.jopit.architecture.components.android.network.url

import androidx.annotation.RestrictTo
import com.gperre.jopit.architecture.components.android.BuildConfig
import com.gperre.jopit.architecture.components.android.application.properties.ApplicationProperties
import javax.inject.Inject

@RestrictTo(RestrictTo.Scope.LIBRARY)
class URLBuilder @Inject constructor(
    private val properties: ApplicationProperties
) {

    fun getBaseURL() = if (properties.isDemo()) {
        "https://api-staging.jopit.com.ar/"
    } else {
        "https://api.jopit.com.ar/"
    }
}
