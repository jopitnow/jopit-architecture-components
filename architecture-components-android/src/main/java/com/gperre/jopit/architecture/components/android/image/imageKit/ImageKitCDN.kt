package com.gperre.jopit.architecture.components.android.image.imageKit

import android.content.Context
import com.gperre.jopit.architecture.components.android.image.ImageAdapter
import com.imagekit.android.ImageKit
import com.imagekit.android.entity.TransformationPosition
import javax.inject.Singleton

@Singleton
class ImageKitCDN (private val context: Context) : ImageAdapter {

    private val imageKit by lazy {
        ImageKit.getInstance()
    }

    init {
        ImageKit.init(
            context = context,
            urlEndpoint = IMAGEKIT_URL,
            transformationPosition = TransformationPosition.PATH,
        )
    }

    override fun getResourceURL(icon: String): String {
        val basicUrl = imageKit
            .url(path = icon, transformationPosition = TransformationPosition.QUERY)
            .addCustomQueryParameter(TR_PARAM, WIDTH_PARAM + getScale().toString())
        return basicUrl.create()
    }

    override fun getResourceURL(icon: String, width: Float, height: Float): String {
        val basicUrl = imageKit
            .url(path = icon, transformationPosition = TransformationPosition.QUERY)
            .addCustomQueryParameter(TR_PARAM, WIDTH_PARAM + width)
        return basicUrl.create()
    }

    private fun getScale() = context.resources.displayMetrics.density / MAX_DENSITY_DIVIDER

    companion object {
        private const val TR_PARAM = "tr"
        private const val WIDTH_PARAM = "w-"
        private const val MAX_DENSITY_DIVIDER = 4

        private const val IMAGEKIT_URL = "https://ik.imagekit.io/jopitalpha/res"
    }
}
