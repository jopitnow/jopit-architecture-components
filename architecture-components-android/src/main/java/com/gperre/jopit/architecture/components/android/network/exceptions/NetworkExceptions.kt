package com.gperre.jopit.architecture.components.android.network.exceptions

import java.io.IOException

class URLException(customMessage: String) : Exception(customMessage)

class NoNetworkException(override val message: String) : IOException() {
    override fun toString(): String {
        return "NoNetworkException{url='$message'}"
    }
}
