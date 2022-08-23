package com.gperre.jopit.architecture.components.android.network.retrofit

import androidx.annotation.RestrictTo
import com.google.gson.GsonBuilder
import com.gperre.jopit.architecture.components.android.network.adapters.apiresult.ApiResultAdapterFactory
import com.gperre.jopit.architecture.components.android.network.okhttp.OkHttpBuilder
import javax.inject.Inject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RestrictTo(RestrictTo.Scope.LIBRARY)
class RetrofitBuilder @Inject constructor(
    private val okHttpBuilder: OkHttpBuilder,
    private val gsonBuilder: GsonBuilder
) {

    fun getBaseBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
            .addCallAdapterFactory(ApiResultAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
            .client(okHttpBuilder.getBaseBuilder().build())
    }
}
