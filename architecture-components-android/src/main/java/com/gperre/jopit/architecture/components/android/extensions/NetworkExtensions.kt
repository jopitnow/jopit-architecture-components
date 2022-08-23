package com.gperre.jopit.architecture.components.android.extensions

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit

fun Retrofit.Builder.addUniqueInstanceConverterFactory(converter: Converter.Factory): Retrofit.Builder {
    val iterator = this.converterFactories().iterator()
    while (iterator.hasNext()) {
        if (iterator.next().javaClass == converter.javaClass) {
            iterator.remove()
        }
    }
    this.addConverterFactory(converter)
    return this
}

fun OkHttpClient.Builder.addUniqueInstanceInterceptor(interceptor: Interceptor): OkHttpClient.Builder {
    val iterator = this.interceptors().iterator()
    while (iterator.hasNext()) {
        if (iterator.next().javaClass == interceptor.javaClass) {
            iterator.remove()
        }
    }
    this.addInterceptor(interceptor)
    return this
}