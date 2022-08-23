package com.gperre.jopit.architecture.components.android.network.errors.handler

interface BasicError {

    fun code(): String

    fun message(): String

    fun cause(): String
}
