package com.gperre.jopit.architecture.components.android.di

import com.gperre.jopit.architecture.components.android.coroutines.dispatchers.DefaultDispatcherProvider
import com.gperre.jopit.architecture.components.android.coroutines.dispatchers.DispatcherProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CoroutineModule {

    @Binds
    abstract fun bindsDefaultDispatcherProvider(
        repository: DefaultDispatcherProvider
    ): DispatcherProvider
}
