package io.ubyte.tldr.usecases

import io.ubyte.tldr.store.PageStore
import logcat.LogPriority
import logcat.logcat
import javax.inject.Inject

class UpdatePages @Inject constructor(
    private val downloadPages: DownloadPages,
    private val extractPages: ExtractPages,
    private val store: PageStore
) {
    suspend operator fun invoke() {
        try {
            val file = downloadPages()
            val pages = extractPages(file)
            store.updatePages(pages)
        } catch (e: Exception) {
            logcat(LogPriority.WARN) { e.toString() }
        }
    }
}
