package io.ubyte.lethe.usecases

import io.ubyte.lethe.pages.data.ZipFileDownloader
import io.ubyte.lethe.pages.data.ZipFileHandler
import io.ubyte.lethe.store.PageStore
import logcat.LogPriority
import logcat.logcat
import javax.inject.Inject

class UpdatePages @Inject constructor(
    private val downloader: ZipFileDownloader,
    private val zipFileHandler: ZipFileHandler,
    private val store: PageStore
) {
    suspend operator fun invoke() {
        try {
            val file = downloader.downloadFile()
            val pages = zipFileHandler.extractPages(file)
            store.updatePages(pages)
        } catch (e: Exception) {
            logcat(LogPriority.WARN) { e.toString() }
        }
    }
}
