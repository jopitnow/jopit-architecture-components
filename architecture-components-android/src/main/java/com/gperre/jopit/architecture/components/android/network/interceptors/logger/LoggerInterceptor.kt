package com.gperre.jopit.architecture.components.android.network.interceptors.logger

import android.util.Log
import androidx.annotation.RestrictTo
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.gperre.jopit.android.debug.domain.model.debug.networking.NetworkItem
import com.gperre.jopit.android.debug.domain.repositories.debug.DebugRepository
import com.gperre.jopit.architecture.components.android.coroutines.result.onFailure
import com.gperre.jopit.architecture.components.android.coroutines.result.resultOf
import com.gperre.jopit.architecture.components.android.extensions.getFormattedDate
import java.util.*
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import okio.Buffer

@RestrictTo(RestrictTo.Scope.LIBRARY)
class LoggerInterceptor @Inject constructor(
    private val generator: RetrofitCurlGenerator,
    private val repository: DebugRepository
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val response = chain.proceed(original)
        val curl = generator.getCURL(original)
        val item = response.getNetworkItem(curl)

        handleAddNetworkRequest(item)

        return response
    }

    private fun handleAddNetworkRequest(item: NetworkItem) {
        CoroutineScope(Dispatchers.IO).launch {
            setNetworkRequest(item)
                .onFailure {
                    Log.e(NETWORK_REQUEST_TAG, "Error saving network request.")
                }
                .also {
                    Log.i(NETWORK_REQUEST_TAG, Gson().toJson(item))
                }
        }
    }

    private suspend fun setNetworkRequest(item: NetworkItem) = resultOf {
        repository.setNetworkRequest(item)
    }

    private fun Response.getNetworkItem(curl: String) = NetworkItem(
        title = this.request.url.encodedPath,
        date = Date().getFormattedDate(DATE_FORMAT),
        label = this.code.toString(),
        module = "android-module", // TODO: Change to requester module name when it is implemented.
        url = this.request.url.toString(),
        method = this.request.method,
        curl = curl,
        requestBody = if (this.request.headers.isJSONContent()) this.request.body?.string()?.let {
            JsonParser.parseString(it) as? JsonObject
        } else null,
        requestHeaders = this.request.headers.toMap(),
        responseHeaders = this.headers.toMap(),
        responseBody = if (this.headers.isJSONContent()) this.body?.let {
            JsonParser.parseString(peakBody()) as? JsonObject
        } else null
    )

    private fun Headers.toMap(): MutableMap<String, String> {
        val headersMap = mutableMapOf<String, String>()

        for (i in 0 until size) {
            val headerName = name(i)
            val headerValue = value(i)
            headersMap[headerName] = headerValue
        }

        return headersMap
    }

    private fun RequestBody.string() = runCatching {
        val buffer = Buffer()
        writeTo(buffer)
        buffer.readUtf8()
    }.getOrNull()

    private fun Response.peakBody(): String {
        return peekBody(Long.MAX_VALUE).string()
    }

    private fun Headers.isJSONContent(): Boolean {
        return get("Content-Type")?.contains("application/json") ?: false
    }

    companion object {
        private const val DATE_FORMAT = "dd/MM/yy - HH:mm:ss"

        private const val NETWORK_REQUEST_TAG = "NETWORK_REQUEST"
    }
}
