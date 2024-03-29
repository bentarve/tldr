package io.ubyte.tldr.usecases

import io.ubyte.tldr.model.Page
import io.ubyte.tldr.util.AppCoroutineDispatchers
import kotlinx.coroutines.withContext
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath
import okio.openZip
import javax.inject.Inject

class ExtractPages @Inject constructor(
    private val fileSystem: FileSystem,
    private val dispatchers: AppCoroutineDispatchers
) {
    suspend operator fun invoke(path: Path): List<Page> = withContext(dispatchers.io) {
        val zipFile = fileSystem.openZip(path)

        fun toPage(path: Path): Page {
            val platform = path.parent?.name
            val pageContent = zipFile.read(path) { readUtf8().trim() }

            return Page(
                name = path.toString().substringAfterLast("/").removeSuffix(".md"),
                platform = requireNotNull(platform),
                markdown = pageContent
            )
        }

        return@withContext zipFile.list("/".toPath())
            .flatMap(zipFile::list)
            .map(::toPage)
    }
}
