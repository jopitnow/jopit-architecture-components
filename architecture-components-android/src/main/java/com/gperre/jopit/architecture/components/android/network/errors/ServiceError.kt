package com.gperre.jopit.architecture.components.android.network.errors

data class ServiceError(
    val internalCode: Int,
    val remoteCode: Int,
    val message: String? = null,
    val response: String? = null
)
