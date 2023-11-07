package com.gperre.jopit.architecture.components.android.network.adapters.apiresult

import com.gperre.jopit.architecture.components.android.network.errors.managers.ServiceErrorManager
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class ApiResultCall<T>(
    private val delegate: Call<T>
) : Call<ApiResult<T>> {

    val serviceErrorManager = ServiceErrorManager()

    override fun enqueue(callback: Callback<ApiResult<T>>) {
        delegate.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    callback.onResponse(
                        this@ApiResultCall,
                        Response.success(ApiResult.SUCCESS(response.body()))
                    )
                } else {
                    val serviceError = serviceErrorManager.process(HttpException(response))
                    callback.onResponse(
                        this@ApiResultCall,
                        Response.success(ApiResult.ERROR(serviceError))
                    )
                }

            }

            override fun onFailure(call: Call<T>, throwable: Throwable) {
                val serviceError = serviceErrorManager.process(throwable)
                callback.onResponse(
                    this@ApiResultCall,
                    Response.success(ApiResult.ERROR(serviceError))
                )
            }
        })
    }

    override fun clone(): Call<ApiResult<T>> = ApiResultCall(delegate.clone())

    override fun execute(): Response<ApiResult<T>> {
        throw UnsupportedOperationException("NetworkResponseCall doesn't support execute")
    }

    override fun isExecuted() = delegate.isExecuted

    override fun cancel() = delegate.cancel()

    override fun isCanceled() = delegate.isCanceled

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}
