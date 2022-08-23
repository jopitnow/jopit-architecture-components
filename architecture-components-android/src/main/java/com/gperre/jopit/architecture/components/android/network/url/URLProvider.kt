package com.gperre.jopit.architecture.components.android.network.url

import javax.inject.Inject

class URLProvider @Inject constructor(
    private val urlBuilder: URLBuilder
) {

    fun get(type: URLType): String {
        return when (type) {
            URLType.BASE_MOBILE_SERVICES -> urlBuilder.getBaseURL()
            URLType.BASE_MOBILE_MOCK -> urlBuilder.getMockURL()
        }
    }
}
