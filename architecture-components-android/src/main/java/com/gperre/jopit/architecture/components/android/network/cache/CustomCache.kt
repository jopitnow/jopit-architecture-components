package com.gperre.jopit.architecture.components.android.network.cache

import com.gperre.jopit.architecture.components.android.network.cache.models.Entry
import com.gperre.jopit.architecture.components.android.network.cache.utils.FileUtils
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton
import okhttp3.Request
import okhttp3.Response
import okio.ByteString

@Singleton
class CustomCache @Inject constructor(
    private val cacheDir: File,
    private val fileUtils: FileUtils
) {

    fun write(request: Request, response: Response) {
        if (!response.isSuccessful) return
        fileUtils.parse(request.url.toString())?.lastPathSegment?.let { filename ->
            deleteIfExists(filename)
            saveHeader(response, filename)
            saveBody(response, filename)
        }
    }

    fun read(request: Request, maxTime: Int): ByteString? {
        fileUtils.parse(request.url.toString())?.lastPathSegment?.let { filename ->
            if (checkValidate(request, filename, maxTime)) {
                getFile(filename)?.let {
                    return ByteString.of(*fileUtils.readBytes(it))
                }
            }
        }
        return null
    }

    private fun checkValidate(request: Request, filename: String, maxTime: Int): Boolean {
        getFile("$HEADER_PREFIX$filename")?.let {
            return fileUtils.readEntry(it)?.matches(request, maxTime) ?: false
        }
        return false
    }

    private fun saveHeader(response: Response, filename: String) {
        val new = createFile("$HEADER_PREFIX$filename")
        val entry = Entry(response)
        fileUtils.write(new, entry.writeTo())
    }

    private fun saveBody(response: Response, filename: String) {
        val new = createFile(filename)
        val source = response.body?.source()
        source?.request(Integer.MAX_VALUE.toLong())
        source?.buffer()?.snapshot()?.write(new.outputStream())
    }

    private fun createFile(filename: String): File {
        if (!cacheDir.exists()) cacheDir.mkdirs()
        return fileUtils.create(filename, cacheDir)
    }

    private fun deleteIfExists(filename: String) {
        val bFiles = fileUtils.listFiles(cacheDir, filename)
        val hFiles = fileUtils.listFiles(cacheDir, "$HEADER_PREFIX$filename")
        if (bFiles != null && bFiles.isNotEmpty()) {
            bFiles[0].delete()
        }
        if (hFiles != null && hFiles.isNotEmpty()) {
            hFiles[0].delete()
        }
    }

    private fun getFile(filename: String): File? {
        val files = fileUtils.listFiles(cacheDir, filename)
        return if (files != null && files.isNotEmpty()) {
            files[0]
        } else null
    }

    companion object {
        private const val HEADER_PREFIX = "h-"
    }
}
