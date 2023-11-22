package com.gperre.jopit.architecture.components.android.di

import android.content.Context
import com.google.gson.GsonBuilder
import com.gperre.jopit.architecture.components.android.network.cache.CustomCache
import com.gperre.jopit.architecture.components.android.network.okhttp.OkHttpBuilder
import com.gperre.jopit.architecture.components.android.network.retrofit.RetrofitBuilder
import com.gperre.jopit.architecture.components.android.network.retrofit.RetrofitGenerator
import com.gperre.jopit.architecture.components.android.network.url.URLProvider
import com.gperre.jopit.architecture.components.android.network.utils.DoubleTypeAdapter
import com.gperre.jopit.architecture.components.android.network.utils.NetworkUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.File
import javax.inject.Named
import javax.inject.Singleton
import okhttp3.Cache
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun providesFile(@ApplicationContext appContext: Context): File {
        return File(appContext.cacheDir, CACHE_DIR)
    }

    @Provides
    @Named(NAMED_CACHE)
    fun providesCache(file: File): Cache {
        val cacheSize: Long = 10 * 1024 * 1024 // 10 MiB
        return Cache(file, cacheSize)
    }

    @Provides
    fun providesGsonBuilder(): GsonBuilder {
        return GsonBuilder()
            .setDateFormat(DATE_FORMAT_SERVER)
            .registerTypeAdapter(Double::class.java, DoubleTypeAdapter())
    }

    @Provides
    fun providesNetworkUtil(
        @ApplicationContext context: Context
    ): NetworkUtils = NetworkUtils(context)

    @Provides
    fun providesRetrofitClient(
        gson: GsonBuilder,
        okHttpBuilder: OkHttpBuilder,
        retrofitBuilder: RetrofitBuilder,
        postCache: CustomCache,
        networkUtils: NetworkUtils,
        urlProvider: URLProvider
    ): RetrofitGenerator {
        return RetrofitGenerator(
            gson = gson,
            okHttpBuilder = okHttpBuilder.getBaseBuilder(),
            retrofitBuilder = retrofitBuilder,
            postCache = postCache,
            networkUtils = networkUtils,
            urlProvider = urlProvider
        )
    }

    @Provides
    fun providesRetrofitBuilder(
        retrofitBuilder: RetrofitBuilder
    ): Retrofit.Builder = retrofitBuilder.getBaseBuilder()

    companion object {
        private const val DATE_FORMAT_SERVER = "yyyy-MM-dd'T'HH:mm:ss'Z'"
        const val CACHE_DIR = "http-cache"
        const val NAMED_CACHE = "injected-cache"
    }
}
