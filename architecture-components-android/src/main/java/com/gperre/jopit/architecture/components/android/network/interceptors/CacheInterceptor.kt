package com.gperre.jopit.architecture.components.android.network.interceptors

import com.gperre.jopit.architecture.components.android.extensions.ZERO_VALUE
import com.gperre.jopit.architecture.components.android.extensions.empty
import com.gperre.jopit.architecture.components.android.network.annotations.CacheInfo
import com.gperre.jopit.architecture.components.android.network.annotations.NotModifiedHandled
import com.gperre.jopit.architecture.components.android.network.cache.CustomCache
import com.gperre.jopit.architecture.components.android.network.interceptors.models.InterceptorData.CACHE_CONTROL
import com.gperre.jopit.architecture.components.android.network.interceptors.models.InterceptorData.CACHE_PUBLIC
import com.gperre.jopit.architecture.components.android.network.interceptors.models.InterceptorData.CONTENT_TYPE_VALUE
import com.gperre.jopit.architecture.components.android.network.interceptors.models.InterceptorData.GET_METHOD
import com.gperre.jopit.architecture.components.android.network.utils.NetworkUtils
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import retrofit2.Invocation
import java.util.concurrent.TimeUnit

internal class CacheInterceptor (
    private val networkUtils: NetworkUtils,
    private val postCache: CustomCache,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val invocation: Invocation? = request.tag(Invocation::class.java)

        val cacheInfo = invocation?.method()?.let {
            if (it.getAnnotation(NotModifiedHandled::class.java) != null) {
                return chain.proceed(request)
            }
            it.getAnnotation(CacheInfo::class.java)
        } ?: return chain.proceed(request)

        return if (request.method != GET_METHOD) {
            postCache(chain, getTime(cacheInfo))
        } else {
            if (networkUtils.isNotConnected()) {
                chain.proceed(cacheOffline(chain, cacheInfo))
            } else {
                val response = chain.proceed(chain.request())
                val cache = cacheOnline(cacheInfo)
                response.newBuilder()
                    .header(CACHE_CONTROL, "$CACHE_PUBLIC$cache")
                    .build()
            }
        }
    }

    private fun cacheOnline(cacheInfo: CacheInfo): CacheControl {
        val cacheControl = CacheControl.Builder()
        val time = cacheInfo.lifeTimeCache
        if (time == Int.ZERO_VALUE) {
            cacheControl.noCache()
        } else {
            cacheControl.maxAge(time, TimeUnit.SECONDS)
        }
        return cacheControl.build()
    }

    private fun cacheOffline(chain: Interceptor.Chain, cacheInfo: CacheInfo): Request {
        val request = chain.request()
        val cacheControl = CacheControl.Builder()
            .maxStale(cacheInfo.lifeTimeOffline * 24 * 60 * 60, TimeUnit.SECONDS)
            .onlyIfCached()
            .build()

        return request.newBuilder()
            .cacheControl(cacheControl)
            .build()
    }

    private fun getTime(cacheInfo: CacheInfo): Int {
        return if (!networkUtils.isConnected()) {
            cacheInfo.lifeTimeOffline * 24 * 60 * 60
        } else {
            cacheInfo.lifeTimeCache
        }
    }

    private fun postCache(
        chain: Interceptor.Chain,
        maxTime: Int
    ): Response {
        val body = postCache.read(chain.request(), maxTime)
        body?.let {
            return Response.Builder().request(chain.request())
                .request(chain.request())
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .message(String.empty())
                .body(ResponseBody.create(CONTENT_TYPE_VALUE.toMediaTypeOrNull(), body))
                .build()
        } ?: run {
            val response = chain.proceed(chain.request())
            postCache.write(chain.request(), response)
            return response
        }
    }
}
