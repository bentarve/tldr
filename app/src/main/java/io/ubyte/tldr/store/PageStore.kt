package io.ubyte.tldr.store

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
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
    suspend fun updatePages(pages: List<Page>) = withContext(dispatchers.io) {
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

    suspend fun count(): Long = withContext(dispatchers.io) {
        db.count().executeAsOne()
    }
}
