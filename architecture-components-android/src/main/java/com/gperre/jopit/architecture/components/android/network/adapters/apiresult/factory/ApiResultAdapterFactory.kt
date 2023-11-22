package com.gperre.jopit.architecture.components.android.network.adapters.apiresult.factory

import com.gperre.jopit.architecture.components.android.network.adapters.apiresult.content.ApiResult
import com.gperre.jopit.architecture.components.android.network.adapters.apiresult.content.ApiResultAdapter
import com.gperre.jopit.architecture.components.android.network.adapters.apiresult.empty.EmptyApiResult
import com.gperre.jopit.architecture.components.android.network.adapters.apiresult.empty.EmptyApiResultAdapter
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit

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
        val rawResponseType = getRawType(responseType)
        if (rawResponseType != ApiResult::class.java && rawResponseType != EmptyApiResult::class.java) {
            return null
        }

        return if (rawResponseType == EmptyApiResult::class.java) {
            EmptyApiResultAdapter()
        } else {
            check(responseType is ParameterizedType) {
                "Response must be parameterized as ApiResult<Foo> or ApiResult<out Foo>"
            }

            val successBodyType = getParameterUpperBound(0, responseType)
            ApiResultAdapter<Any>(successBodyType)
        }
    }
}
