package com.gperre.jopit.architecture.components.android.network.adapters.apiresult.empty

import com.gperre.jopit.architecture.components.android.network.errors.managers.ServiceErrorManager
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class EmptyApiResultCall(
    private val delegate: Call<Unit>
) : Call<EmptyApiResult> {

    val serviceErrorManager = ServiceErrorManager()

    override fun enqueue(callback: Callback<EmptyApiResult>) {
        delegate.enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful) {
                    callback.onResponse(
                        this@EmptyApiResultCall,
                        Response.success(EmptyApiResult.SUCCESS)
                    )
                } else {
                    val serviceError = serviceErrorManager.process(HttpException(response))
                    callback.onResponse(
                        this@EmptyApiResultCall,
                        Response.success(EmptyApiResult.ERROR(serviceError))
                    )
                }

            }

            override fun onFailure(call: Call<Unit>, throwable: Throwable) {
                val serviceError = serviceErrorManager.process(throwable)
                callback.onResponse(
                    this@EmptyApiResultCall,
                    Response.success(EmptyApiResult.ERROR(serviceError))
                )
            }
        })
    }

    override fun clone(): Call<EmptyApiResult> = EmptyApiResultCall(delegate.clone())

    override fun execute(): Response<EmptyApiResult> {
        throw UnsupportedOperationException("NetworkResponseCall doesn't support execute")
    }

    override fun isExecuted() = delegate.isExecuted

    override fun cancel() = delegate.cancel()

    override fun isCanceled() = delegate.isCanceled

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}
