package com.gperre.jopit.architecture.components.android.network.cache.utils

import android.net.Uri
import com.gperre.jopit.architecture.components.android.network.cache.models.Entry
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import javax.inject.Inject

class FileUtils @Inject constructor() {

    /**
     * Use an utils in order to make possible a mock
     */
    fun parse(url: String): Uri? {
        return Uri.parse(url)
    }

    fun create(filename: String, cacheDir: File): File {
        return File.createTempFile(filename, null, cacheDir)
    }

    fun listFiles(dir: File, filename: String): Array<File>? {
        return dir.listFiles { _, name ->
            name.startsWith(filename, ignoreCase = true)
        }
    }

    fun write(file: File, text: String) {
        file.writeText(text)
    }

    fun readEntry(file: File): Entry? {
        return try {
            Entry(BufferedReader(InputStreamReader(file.inputStream())))
        } catch (exception: Exception) {
            exception.printStackTrace()
            null
        }
    }

    fun readBytes(file: File): ByteArray {
        return file.readBytes()
    }
}
