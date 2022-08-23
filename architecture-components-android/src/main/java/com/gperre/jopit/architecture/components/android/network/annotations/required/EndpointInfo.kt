package com.gperre.jopit.architecture.components.android.network.annotations.required

import com.gperre.jopit.architecture.components.android.network.url.URLType

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class EndpointInfo(
    val type: URLType = URLType.BASE_MOBILE_SERVICES
)
