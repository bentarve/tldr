package io.ubyte.lethe.usecases

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.utils.io.jvm.javaio.*
import io.ubyte.lethe.core.util.AppCoroutineDispatchers
import kotlinx.coroutines.withContext
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@Suppress("BlockingMethodInNonBlockingContext")
class DownloadPages @Inject constructor(
    private val fileSystem: FileSystem,
    private val httpClient: HttpClient,
    private val dispatchers: AppCoroutineDispatchers
) {
    suspend operator fun invoke(): Path = withContext(dispatchers.io) {
        val temporaryFile = (FileSystem.SYSTEM_TEMPORARY_DIRECTORY.toString() +
                Path.DIRECTORY_SEPARATOR +
                FILE_NAME).toPath()

        fileSystem.write(temporaryFile) {
            httpClient.use { client ->
                client.get(ZIP_ASSET_URL)
                    .bodyAsChannel()
                    .copyTo(outputStream())
            }
        }
        return@withContext temporaryFile
    }
}

private const val FILE_NAME = "tldr.zip"
private const val ZIP_ASSET_URL = "https://tldr.sh/assets/tldr.zip"
