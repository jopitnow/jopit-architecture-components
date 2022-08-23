package com.gperre.jopit.architecture.components.android.network.utils

import com.google.gson.JsonSyntaxException
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.io.IOException

internal class DoubleTypeAdapter: TypeAdapter<Number>() {

    @Throws(IOException::class)
    override fun write(out: JsonWriter, value: Number) {
        out.value(value)
    }

    @Throws(IOException::class)
    override fun read(json: JsonReader): Number? {
        if (json.peek() == JsonToken.NULL) {
            json.nextNull()
            return null
        }
        try {
            val result = json.nextString()
            if ("" == result) {
                return null
            }
            return if (NULL_STRING.equals(result, ignoreCase = true)) {
                null
            } else java.lang.Double.parseDouble(result)
        } catch (e: NumberFormatException) {
            throw JsonSyntaxException(e)
        }
    }

    companion object {
        private const val NULL_STRING = "null"
    }
}