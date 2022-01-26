package io.ubyte.lethe.di

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.*
import io.ubyte.lethe.Database
import io.ubyte.lethe.pages.data.ZipFileDownloader
import io.ubyte.lethe.pages.data.ZipFileHandler
import okio.FileSystem
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideFileSystem() = FileSystem.SYSTEM

    @Singleton
    @Provides
    fun provideDownloader(fileSystem: FileSystem, httpClient: HttpClient): ZipFileDownloader {
        return ZipFileDownloader(fileSystem, httpClient)
    }

    @Provides
    fun provideZipFileHandler(fileSystem: FileSystem) = ZipFileHandler(fileSystem)

    @Singleton
    @Provides
    fun provideHttpClient() = HttpClient(OkHttp) { BrowserUserAgent() }

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): Database {
        val driver: SqlDriver = AndroidSqliteDriver(Database.Schema, context, "man-pages.db")
        return Database(driver)
    }
}
