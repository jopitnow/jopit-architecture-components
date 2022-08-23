package com.gperre.jopit.architecture.components.android.network.errors.dto

import com.google.gson.annotations.SerializedName
import com.gperre.jopit.architecture.components.android.extensions.valueOrDefault
import com.gperre.jopit.architecture.components.android.network.errors.handler.BasicError
import java.io.Serializable

data class WSError(

    @SerializedName("code", alternate = ["message"])
    var code: String? = null,

    @SerializedName("errorDetail", alternate = ["cause"])
    val errorDetail: String? = null,

    @SerializedName("messages")
    val messages: List<String> = emptyList(),

    @SerializedName("errorCategory")
    val errorCategory: String? = null
) : Serializable, BasicError {

    override fun cause(): String = errorDetail.valueOrDefault()

    override fun code(): String = code.valueOrDefault()

    override fun message(): String = code.valueOrDefault()
}
