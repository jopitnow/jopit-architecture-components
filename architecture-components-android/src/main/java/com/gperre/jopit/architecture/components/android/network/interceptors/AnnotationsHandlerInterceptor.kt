package com.gperre.jopit.architecture.components.android.network.interceptors

import com.gperre.jopit.architecture.components.android.network.annotations.required.EndpointInfo
import com.gperre.jopit.architecture.components.android.network.annotations.required.ServiceClient
import com.gperre.jopit.architecture.components.android.network.annotations.Timeout
import com.gperre.jopit.architecture.components.android.network.exceptions.URLException
import com.gperre.jopit.architecture.components.android.network.url.URLProvider
import java.lang.reflect.Method
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Invocation
import java.util.concurrent.TimeUnit
import okhttp3.Request

internal class AnnotationsHandlerInterceptor(
    private val urlProvider: URLProvider
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val invocation = request.tag(Invocation::class.java)

        invocation?.method()?.let { method ->
            chain.setTimeout(method)

            val endpointInfo = method.getAnnotation(EndpointInfo::class.java)
            val serviceClient = method.declaringClass.getAnnotation(ServiceClient::class.java)

            if (endpointInfo == null && serviceClient == null) {
                throw URLException("$URL_EXCEPTION_MESSAGE ${method.name}.")
            } else if (endpointInfo != null && serviceClient != null) {
                return chain.proceed(
                    request
                        .newBuilder()
                        .url(getURL(request, serviceClient, endpointInfo))
                        .build()
                )
            }
        }

        return chain.proceed(request)
    }

    private fun getURL(
        request: Request,
        serviceClient: ServiceClient,
        endpointInfo: EndpointInfo,
    ): String {
        val serviceUrl = urlProvider.get(serviceClient.type)
        val methodUrl = urlProvider.get(endpointInfo.type)
        return request.url.toString().replace(serviceUrl, methodUrl)
    }

    private fun Interceptor.Chain.setTimeout(method: Method) {
        method.getAnnotation(Timeout::class.java)?.let {
            withConnectTimeout(it.value.toInt(), TimeUnit.MILLISECONDS)
            withReadTimeout(it.value.toInt(), TimeUnit.MILLISECONDS)
            withWriteTimeout(it.value.toInt(), TimeUnit.MILLISECONDS)
        }
    }

    companion object {
        private const val URL_EXCEPTION_MESSAGE = "Missing ServiceClient or EndpointInfo annotation for"
    }
}
