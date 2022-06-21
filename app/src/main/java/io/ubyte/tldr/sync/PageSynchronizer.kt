package io.ubyte.tldr.sync

import io.ubyte.tldr.settings.SettingsStore
import io.ubyte.tldr.store.PageStore
import io.ubyte.tldr.sync.PageSynchronizer.SyncState.*
import io.ubyte.tldr.usecases.DownloadPages
import io.ubyte.tldr.usecases.ExtractPages
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import logcat.LogPriority
import logcat.logcat
import java.time.Duration
import java.time.Instant
import javax.inject.Inject

class PageSynchronizer @Inject constructor(
    private val downloadPages: DownloadPages,
    private val extractPages: ExtractPages,
    private val store: PageStore,
    private val settingsStore: SettingsStore
) {
    private val _state = MutableStateFlow<SyncState?>(null)
    val state = _state.asStateFlow()

    suspend fun sync(forceSync: Boolean = false) {
        if (store.count() == 0L) {
            _state.emit(INITIAL_SYNC)
        } else if (forceSync || checkForUpdates()) {
            _state.emit(SYNC)
        } else {
            _state.emit(IDLE)
            return
        }

        val result = if (updatePages()) IDLE else FAILED
        _state.emit(result)
    }

    private suspend fun updatePages(): Boolean {
        val zipFile = try {
            downloadPages()
        } catch (e: Exception) {
            logcat(LogPriority.WARN) { "Could not download pages" }
            return false
        }

        val pages = try {
            extractPages(zipFile)
        } catch (e: Exception) {
            logcat(LogPriority.WARN) { "Could not unzip file" }
            return false
        }

        try {
            store.updatePages(pages)
        } catch (e: Exception) {
            logcat(LogPriority.WARN) { "Could not save pages" }
            return false
        }

        try {
            settingsStore.setLastSync(Instant.now().epochSecond)
        } catch (e: Exception) {
            logcat(LogPriority.WARN) { "Could not log synchronization" }
            return false
        }

        logcat(LogPriority.INFO) { "Page synchronization completed" }
        return true
    }

    private suspend fun checkForUpdates(): Boolean {
        try {
            val lastSync = Instant.ofEpochSecond(settingsStore.getLastSync())
            val duration = Duration.between(lastSync, Instant.now()).toDays()

            if (duration > 45) return true
        } catch (e: Exception) {
            logcat(LogPriority.WARN) { "Could not read last synchronization" }
        }
        return false
    }

    enum class SyncState {
        IDLE, INITIAL_SYNC, SYNC, FAILED
    }
}
