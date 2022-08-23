package com.gperre.jopit.architecture.components.android.network.interceptors.header

import androidx.annotation.RestrictTo
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

@RestrictTo(RestrictTo.Scope.LIBRARY)
class HeaderInterceptor @Inject constructor(
    private val headerAttaching: WebHeaderAttaching
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val requestBuilder = original.newBuilder()
        headerAttaching.attachHttpInterceptors(requestBuilder)
        requestBuilder.method(original.method, original.body)
        return chain.proceed(requestBuilder.build())
    }
}
