package com.gperre.jopit.architecture.components.android.network.adapters.apiresult.empty

import java.lang.reflect.Type
import retrofit2.Call
import retrofit2.CallAdapter

class EmptyApiResultAdapter : CallAdapter<Unit, Call<EmptyApiResult>> {

    override fun responseType(): Type = Unit::class.java

    override fun adapt(call: Call<Unit>): Call<EmptyApiResult> {
        return EmptyApiResultCall(call)
    }
}
