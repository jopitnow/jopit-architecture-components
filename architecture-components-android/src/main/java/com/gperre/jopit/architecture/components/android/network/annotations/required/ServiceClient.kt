package com.gperre.jopit.architecture.components.android.network.annotations.required

import com.gperre.jopit.architecture.components.android.network.url.URLType

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class ServiceClient(
    val type: URLType = URLType.BASE_MOBILE_SERVICES
)
