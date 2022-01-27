package io.ubyte.lethe.pages.data

import io.ubyte.lethe.pages.model.Page
import io.ubyte.lethe.pages.model.Platform
import io.ubyte.lethe.util.AppCoroutineDispatchers
import kotlinx.coroutines.withContext
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath
import okio.openZip
import javax.inject.Inject

@Suppress("BlockingMethodInNonBlockingContext")
class ZipFileHandler @Inject constructor(
    private val fileSystem: FileSystem,
    private val dispatcher: AppCoroutineDispatchers
) {
    private lateinit var zipFile: FileSystem

    suspend fun extractPages(
        path: Path,
        selectedPlatforms: Regex = Platform.all()
    ): List<Page> = withContext(dispatcher.io) {
        zipFile = fileSystem.openZip(path)

        return@withContext zipFile.list(ROOT.toPath())
            .filter { it.name matches selectedPlatforms }
            .flatMap(zipFile::list)
            .map(::toPage)
    }

    private fun toPage(path: Path): Page {
        val pageContent = zipFile.read(path) { readUtf8().trim() }
        return Page(
            name = pageContent.lines().first().removePrefix("#").trim(),
            platform = checkNotNull(path.parent).name,
            markdown = pageContent
        )
    }
}

/*
* all supported languages: "/"
* english only: "/pages"
* */
private const val ROOT = "/pages"
