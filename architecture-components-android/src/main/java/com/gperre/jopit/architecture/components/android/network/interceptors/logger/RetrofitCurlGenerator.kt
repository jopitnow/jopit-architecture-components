package com.gperre.jopit.architecture.components.android.network.interceptors.logger

import java.nio.charset.Charset
import javax.inject.Inject
import okhttp3.Request
import okhttp3.RequestBody
import okio.Buffer

class RetrofitCurlGenerator @Inject constructor() {

    fun getCURL(request: Request): String {
        var command = ShellCommand("curl", listOf("-X" to request.method))

        request.headers.names().forEach { element ->
            command = command.argument("-H", "'$element: ${request.header(element)}'")
        }

        val body = request.body
        if (body != null) {
            if (body.contentType() != null) {
                command = command.argument("-H", "'Content-Type: ${body.contentType()}'")
            }
            if (body.contentLength() != (-1).toLong()) {
                command = command.argument("-H", "'Content-Length: ${body.contentLength()}'")
            }
            if (body.toCommand() != null) {
                command = command.argument("-d" to "'${body.toCommand()}'")
            }
        }

        command = command.argument("-i" to "'${request.url}'")

        return command.toString()
    }

    private fun RequestBody.toCommand(): String? {
        val utf = Charset.forName("UTF-8")
        val buffer = Buffer()
        this.writeTo(buffer)
        val contentType = this.contentType()
        val charset = if (contentType != null) contentType.charset(utf) else utf

        return charset?.let { buffer.readString(it) }
    }
}
