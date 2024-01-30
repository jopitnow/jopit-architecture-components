package com.gperre.jopit.architecture.components.android.network.interceptors.logger

import android.util.Log
import androidx.annotation.RestrictTo
import javax.inject.Inject
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.*

@RestrictTo(RestrictTo.Scope.LIBRARY)
class LoggerInterceptor @Inject constructor(
    private val generator: RetrofitCurlGenerator
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val curl = generator.getCURL(original)

        Log.i("HTTP_REQUEST", curl)

        return chain.proceed(original)
    }
}
