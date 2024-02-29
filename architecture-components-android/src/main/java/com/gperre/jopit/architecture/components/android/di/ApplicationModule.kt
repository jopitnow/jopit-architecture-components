package com.gperre.jopit.architecture.components.android.di

import com.gperre.jopit.architecture.components.android.application.properties.ApplicationProperties
import com.gperre.jopit.architecture.components.android.application.properties.local.MemoryApplicationProperties
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ApplicationBinds {

    @Binds
    abstract fun providesApplicationProperties(properties: MemoryApplicationProperties): ApplicationProperties
}
