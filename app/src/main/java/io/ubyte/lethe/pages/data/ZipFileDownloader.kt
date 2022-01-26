package io.ubyte.lethe.pages.data

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.utils.io.*
import io.ktor.utils.io.jvm.javaio.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath
import javax.inject.Inject

@Suppress("BlockingMethodInNonBlockingContext")
class ZipFileDownloader @Inject constructor(
    private val fileSystem: FileSystem,
    private val httpClient: HttpClient,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    private val temporaryFile = (FileSystem.SYSTEM_TEMPORARY_DIRECTORY.toString() +
            Path.DIRECTORY_SEPARATOR +
            FILE_NAME).toPath()

    suspend fun downloadFile(): Path = withContext(dispatcher) {
        fileSystem.write(temporaryFile) {
            httpClient.use { client ->
                client.get<ByteReadChannel>(ZIP_ASSET_URL)
                    .copyTo(outputStream())
            }
        }

        return@withContext temporaryFile
    }
}

private const val FILE_NAME = "tldr.zip"
private const val ZIP_ASSET_URL = "https://tldr.sh/assets/tldr.zip"
