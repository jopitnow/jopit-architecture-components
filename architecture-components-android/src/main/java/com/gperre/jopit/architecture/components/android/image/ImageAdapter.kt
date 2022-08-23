package com.gperre.jopit.architecture.components.android.image

interface ImageAdapter {

    fun getResourceURL(icon: String): String

    fun getResourceURL(icon: String, width: Float, height: Float): String
}
