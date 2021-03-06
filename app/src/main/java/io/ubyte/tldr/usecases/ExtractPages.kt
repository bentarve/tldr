package io.ubyte.tldr.usecases

import io.ubyte.tldr.model.Page
import io.ubyte.tldr.model.Platform
import io.ubyte.tldr.util.AppCoroutineDispatchers
import kotlinx.coroutines.withContext
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath
import okio.openZip
import javax.inject.Inject

@Suppress("BlockingMethodInNonBlockingContext")
class ExtractPages @Inject constructor(
    private val fileSystem: FileSystem,
    private val dispatchers: AppCoroutineDispatchers
) {
    suspend operator fun invoke(
        path: Path,
        selectedPlatforms: Regex = Platform.allPlatforms()
    ): List<Page> = withContext(dispatchers.io) {
        val zipFile = fileSystem.openZip(path)

        fun toPage(path: Path): Page {
            val platform = path.parent?.name?.let { Platform.formatFromLowercase(it) }
            val pageContent = zipFile.read(path) { readUtf8().trim() }

            return Page(
                name = pageContent.substringBefore("\n").removePrefix("#").trim(),
                platform = requireNotNull(platform),
                markdown = pageContent
            )
        }

        return@withContext zipFile.list(ROOT.toPath())
            .filter { it.name matches selectedPlatforms }
            .flatMap(zipFile::list)
            .map(::toPage)
    }
}

/*
* all supported languages: "/"
* english only: "/pages"
* */
private const val ROOT = "/pages"
