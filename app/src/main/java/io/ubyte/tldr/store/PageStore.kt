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
            val ids = db.findAllPageIds().executeAsList().toMutableSet()

            for (page in pages) {
                db.updatePage(page.name, page.platform, page.markdown)
                if (db.changes().executeAsOne() != 0L) {
                    ids -= db.findPageId(page.name, page.platform).executeAsOne()
                } else {
                    db.insertPage(page.name, page.platform, page.markdown)
                }
            }

            ids.chunked(999).forEach {
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
