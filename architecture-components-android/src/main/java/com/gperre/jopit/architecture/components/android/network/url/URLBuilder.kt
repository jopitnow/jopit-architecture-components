package com.gperre.jopit.architecture.components.android.network.url

import android.content.pm.ApplicationInfo
import androidx.annotation.RestrictTo
import javax.inject.Inject

@RestrictTo(RestrictTo.Scope.LIBRARY)
class URLBuilder constructor(
    applicationInfo: ApplicationInfo
) {

    private val isDebuggable = 0 != applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE

    fun getBaseURL() = if (isDebuggable) {
        "https://stg.jopit.com/mobile/"
    } else {
        "https://prod.jopit.com/mobile/"
    }

    fun getMockURL() = "https://private-f0a573-jopittest.apiary-mock.com/"
}
