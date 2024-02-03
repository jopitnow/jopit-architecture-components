package com.gperre.jopit.architecture.components.android.network.interceptors.logger

import android.util.Log
import androidx.annotation.RestrictTo
import com.google.gson.Gson
import com.gperre.jopit.android.debug.domain.model.debug.networking.NetworkItem
import com.gperre.jopit.android.debug.domain.repositories.debug.DebugRepository
import com.gperre.jopit.architecture.components.android.BuildConfig
import com.gperre.jopit.architecture.components.android.coroutines.result.onFailure
import com.gperre.jopit.architecture.components.android.coroutines.result.resultOf
import com.gperre.jopit.architecture.components.android.extensions.getFormattedDate
import java.util.*
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.*

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
        title = this::class.java.simpleName,
        date = Date().getFormattedDate(DATE_FORMAT),
        label = if (isSuccessful) "SUCCESS" else "ERROR",
        module = BuildConfig.LIBRARY_PACKAGE_NAME,
        url = this.request.url.toString(),
        method = this.request.method,
        curl = curl,
        requestHeaders = this.request.headers.toMap(),
        responseHeaders = this.headers.toMap(),
        responseBody = this.body?.string()
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

    companion object {
        private const val DATE_FORMAT = "dd/MM/yy - HH:mm:ss"

        private const val NETWORK_REQUEST_TAG = "NETWORK_REQUEST"
    }
}
