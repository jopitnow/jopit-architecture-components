package com.gperre.jopit.architecture.components.android.network.adapters.apiresult.content

import java.lang.reflect.Type
import retrofit2.Call
import retrofit2.CallAdapter

class ApiResultAdapter<R>(private val successType: Type): CallAdapter<R, Call<ApiResult<R>>> {
    override fun responseType() = successType

    override fun adapt(call: Call<R>): Call<ApiResult<R>> {
        return ApiResultCall(call)
    }
}
