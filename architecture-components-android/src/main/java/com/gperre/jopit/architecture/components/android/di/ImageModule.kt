package com.gperre.jopit.architecture.components.android.di

import android.content.Context
import com.gperre.jopit.architecture.components.android.image.ImageAdapter
import com.gperre.jopit.architecture.components.android.image.imageKit.ImageKitCDN
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ImageModule {

    @Provides
    fun provideImageAdapter(
        @ApplicationContext context: Context
    ): ImageAdapter = ImageKitCDN(context)
}
