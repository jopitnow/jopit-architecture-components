package com.gperre.jopit.architecture.components.android.network.interceptors

import androidx.annotation.RestrictTo
import com.gperre.jopit.architecture.components.android.network.exceptions.NoNetworkException
import com.gperre.jopit.architecture.components.android.network.utils.NetworkUtils
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

@RestrictTo(RestrictTo.Scope.LIBRARY)
class NetworkStateInterceptor @Inject constructor(
    private val networkUtils: NetworkUtils
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        return if (networkUtils.isConnected()) {
            chain.proceed(chain.request())
        } else {
            val url = chain.request().url.toString()
            throw NoNetworkException(message = url)
        }
    }
}
