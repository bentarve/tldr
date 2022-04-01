package io.ubyte.lethe.di

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.*
import io.ubyte.lethe.Database
import io.ubyte.lethe.HistoryQueries
import io.ubyte.lethe.PageQueries
import io.ubyte.lethe.core.util.AppCoroutineDispatchers
import kotlinx.coroutines.Dispatchers
import okio.FileSystem
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideCoroutineDispatchers() = AppCoroutineDispatchers(
        io = Dispatchers.IO,
        computation = Dispatchers.Default,
        main = Dispatchers.Main
    )

    @Singleton
    @Provides
    fun provideFileSystem() = FileSystem.SYSTEM

    @Singleton
    @Provides
    fun provideHttpClient() = HttpClient(OkHttp) { BrowserUserAgent() }

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): Database {
        val driver: SqlDriver = AndroidSqliteDriver(Database.Schema, context, "tldr.db")
        return Database(driver)
    }

    @Singleton
    @Provides
    fun providePageQueries(database: Database): PageQueries {
        return database.pageQueries
    }

    @Singleton
    @Provides
    fun provideHistoryQueries(database: Database): HistoryQueries {
        return database.historyQueries
    }
}
