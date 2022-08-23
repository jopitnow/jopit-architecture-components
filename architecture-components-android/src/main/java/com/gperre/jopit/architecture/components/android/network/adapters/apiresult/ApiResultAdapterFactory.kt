package com.gperre.jopit.architecture.components.android.network.adapters.apiresult

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ApiResultAdapterFactory : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (Call::class.java != getRawType(returnType)) {
            return null
        }

        check(returnType is ParameterizedType) {
            "return type must be parameterized as Call<ApiResult<<Foo>> or Call<ApiResult<out Foo>>"
        }

        val responseType = getParameterUpperBound(0, returnType)
        if (getRawType(responseType) != ApiResult::class.java) {
            return null
        }

        check(responseType is ParameterizedType) { "Response must be parameterized as ApiResult<Foo> or ApiResult<out Foo>" }

        val successBodyType = getParameterUpperBound(0, responseType)

        return ApiResultAdapter<Any>(successBodyType)
    }
}
