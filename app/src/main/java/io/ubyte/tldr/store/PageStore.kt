package io.ubyte.tldr.store

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import io.ubyte.tldr.HistoryQueries
import io.ubyte.tldr.PageQueries
import io.ubyte.tldr.model.Page
import io.ubyte.tldr.model.Page.Companion.mapToPage
import io.ubyte.tldr.model.PageIdentifier.Companion.mapToPageIdentifier
import io.ubyte.tldr.util.AppCoroutineDispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PageStore @Inject constructor(
    private val db: PageQueries,
    private val history: HistoryQueries,
    private val dispatchers: AppCoroutineDispatchers
) {
    suspend fun persistPages(pages: List<Page>) = withContext(dispatchers.io) {
        if (db.count().executeAsOne() == 0L) {
            insertPages(pages)
        } else {
            updatePages(pages)
        }
    }

    private fun insertPages(pages: List<Page>) {
        db.transaction {
            pages.forEach {
                db.insertPage(it.name, it.platform, it.markdown)
            }
        }
    }

    private fun updatePages(pages: List<Page>) {
        db.transaction {
            val existingPages = db.findAllPages().executeAsList()
            val existingPageMap = existingPages.associateBy { it.name + it.platform }

            val pagesToUpdate = mutableListOf<Page>()
            val pagesToInsert = mutableListOf<Page>()

            val pagesToDelete = existingPages.map { it.id }.toMutableSet()

            for (page in pages) {
                val existingPage = existingPageMap[page.name + page.platform]

                if (existingPage != null) {
                    if (existingPage.markdown != page.markdown) {
                        pagesToUpdate.add(page)
                    }
                    pagesToDelete.remove(existingPage.id)
                } else {
                    pagesToInsert.add(page)
                }
            }

            pagesToUpdate.forEach { page ->
                db.updatePage(page.name, page.platform, page.markdown)
            }

            pagesToInsert.forEach { page ->
                db.insertPage(page.name, page.platform, page.markdown)
            }

            pagesToDelete.chunked(999).forEach {
                db.deletePageIds(it)
            }
        }
    }

    suspend fun queryPage(id: Long): Page = withContext(dispatchers.io) {
        db.findPageById(id, ::mapToPage).executeAsOne().also {
            history.insert(id)
        }
    }

    fun queryPages(term: String) = db.queryTerm(term, ::mapToPageIdentifier)
        .asFlow().mapToList(dispatchers.io)

    fun queryMostRecent() = db.mostRecent(::mapToPageIdentifier)
        .asFlow().mapToList(dispatchers.io)

    fun queryMostFrequent() = db.mostFrequent(::mapToPageIdentifier)
        .asFlow().mapToList(dispatchers.io)

    suspend fun count() = withContext(dispatchers.io) {
        db.count().executeAsOne()
    }
}
