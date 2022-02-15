package io.ubyte.lethe.store

import androidx.paging.PagingSource
import com.squareup.sqldelight.Query
import com.squareup.sqldelight.android.paging3.QueryPagingSource
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOne
import io.ubyte.lethe.HistoryQueries
import io.ubyte.lethe.PageQueries
import io.ubyte.lethe.core.util.AppCoroutineDispatchers
import io.ubyte.lethe.model.Page
import io.ubyte.lethe.model.Page.Companion.mapToPage
import io.ubyte.lethe.model.PageIdentifier
import io.ubyte.lethe.model.PageIdentifier.Companion.mapToPageIdentifier
import kotlinx.coroutines.flow.Flow
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

    fun queryPagingSource(): PagingSource<Long, PageIdentifier> {
        return QueryPagingSource(
            countQuery = db.count(),
            transacter = db,
            dispatcher = dispatchers.io,
            queryProvider = ::allPages
        )
    }

    private fun allPages(limit: Long, offset: Long): Query<PageIdentifier> {
        return db.findPages(limit, offset, ::mapToPageIdentifier)
    }

    suspend fun count(): Long = withContext(dispatchers.io) {
        db.count().executeAsOne()
    }
}
