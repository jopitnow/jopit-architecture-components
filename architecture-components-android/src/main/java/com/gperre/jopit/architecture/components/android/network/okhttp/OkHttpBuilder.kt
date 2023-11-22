package com.gperre.jopit.architecture.components.android.network.okhttp

import com.gperre.jopit.architecture.components.android.di.NetworkModule.Companion.NAMED_CACHE
import com.gperre.jopit.architecture.components.android.network.interceptors.NetworkStateInterceptor
import com.gperre.jopit.architecture.components.android.network.interceptors.header.HeaderInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named
import okhttp3.Cache
import okhttp3.OkHttpClient

class OkHttpBuilder @Inject constructor(
    @Named(NAMED_CACHE)
    private val cache: Cache,
    private val networkStateInterceptor: NetworkStateInterceptor,
    private val headerInterceptor: HeaderInterceptor,
) {

    fun getBaseBuilder(): OkHttpClient.Builder {
        val httpClientBuilder = getBaseHttpClient()
        httpClientBuilder.addInterceptor(networkStateInterceptor)
        httpClientBuilder.addInterceptor(headerInterceptor)
        httpClientBuilder.cache(cache)
        return httpClientBuilder
    }

    private fun getBaseHttpClient(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .connectTimeout(SERVICE_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(SERVICE_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(SERVICE_TIMEOUT, TimeUnit.SECONDS)
    }

    companion object {
        private const val SERVICE_TIMEOUT = 20L
    }
}
