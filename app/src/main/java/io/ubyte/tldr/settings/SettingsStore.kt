package io.ubyte.tldr.settings

import androidx.datastore.core.DataStore
import io.ubyte.tldr.Settings
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class SettingsStore @Inject constructor(
    private val dataStore: DataStore<Settings>
) {
    suspend fun setLastSync(timestamp: Long) {
        dataStore.updateData { settings ->
            settings.toBuilder().setLastSync(timestamp).build()
        }
    }

    suspend fun getLastSync() = dataStore.data.first().lastSync
}
