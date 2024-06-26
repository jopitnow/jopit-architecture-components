package com.gperre.jopit.architecture.components.android.network.retrofit

import com.google.gson.GsonBuilder
import com.gperre.jopit.architecture.components.android.extensions.addUniqueInstanceConverterFactory
import com.gperre.jopit.architecture.components.android.extensions.addUniqueInstanceInterceptor
import com.gperre.jopit.architecture.components.android.network.annotations.Timeout
import com.gperre.jopit.architecture.components.android.network.annotations.required.MockClient
import com.gperre.jopit.architecture.components.android.network.annotations.required.ServiceClient
import com.gperre.jopit.architecture.components.android.network.cache.CustomCache
import com.gperre.jopit.architecture.components.android.network.exceptions.URLException
import com.gperre.jopit.architecture.components.android.network.interceptors.CacheInterceptor
import com.gperre.jopit.architecture.components.android.network.url.URLProvider
import com.gperre.jopit.architecture.components.android.network.utils.NetworkUtils
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitGenerator constructor(
    private val gson: GsonBuilder,
    private val okHttpBuilder: OkHttpClient.Builder,
    private val retrofitBuilder: RetrofitBuilder,
    private val postCache: CustomCache,
    private val networkUtils: NetworkUtils,
    private val urlProvider: URLProvider
) {

    fun <T> createRetrofit(service: Class<T>): T {
        setTimeOutClient(service)
        setInterceptors()

        return retrofitBuilder.getBaseBuilder()
            .baseUrl(getURL(service))
            .setFactory()
            .client(okHttpBuilder.build())
            .build()
            .create(service)
    }

    private fun setInterceptors() {
        okHttpBuilder.addUniqueInstanceInterceptor(CacheInterceptor(networkUtils, postCache))
    }

    private fun <T> getURL(service: Class<T>): String {
        return getMockURL(service) ?: getClientURL(service)
    }

    private fun <T> getClientURL(service: Class<T>): String {
        return service.getAnnotation(ServiceClient::class.java)?.type?.let { type ->
            urlProvider.get(type)
        } ?: throw URLException("$URL_EXCEPTION_MESSAGE ${service.name}.")
    }

    private fun <T> getMockURL(service: Class<T>): String? {
        return service.getAnnotation(MockClient::class.java)?.url
    }

    private fun <T> setTimeOutClient(service: Class<T>) {
        service.getAnnotation(Timeout::class.java)?.let {
            okHttpBuilder.connectTimeout(it.value, it.unit)
            okHttpBuilder.writeTimeout(it.value, it.unit)
            okHttpBuilder.readTimeout(it.value, it.unit)
        }
    }

    private fun Retrofit.Builder.setFactory(): Retrofit.Builder {
        return addUniqueInstanceConverterFactory(GsonConverterFactory.create(gson.create()))
    }

    companion object {
        private const val URL_EXCEPTION_MESSAGE = "Missing ServiceClient annotation for class"
    }
}
