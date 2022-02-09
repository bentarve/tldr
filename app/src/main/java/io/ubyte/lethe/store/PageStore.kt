package io.ubyte.lethe.store

import androidx.paging.PagingSource
import com.squareup.sqldelight.android.paging3.QueryPagingSource
import io.ubyte.lethe.Page
import io.ubyte.lethe.PageQueries
import io.ubyte.lethe.core.util.AppCoroutineDispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import io.ubyte.lethe.model.Page as DomainPage

@Singleton
class PageStore @Inject constructor(
    private val db: PageQueries,
    private val dispatchers: AppCoroutineDispatchers
) {
    suspend fun updatePages(pages: List<DomainPage>) = withContext(dispatchers.io) {
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

    fun queryPage(id: Long): DomainPage {
        return db.findPage(id) { name, platform, markdown ->
            DomainPage(name, platform, markdown)
        }.executeAsOne()
    }

    fun queryPagingSource(): PagingSource<Long, Page> {
        return QueryPagingSource(
            countQuery = db.count(),
            transacter = db,
            dispatcher = dispatchers.io,
            queryProvider = db::allPages
        )
    }

    fun count(): Long = db.count().executeAsOne()
}
