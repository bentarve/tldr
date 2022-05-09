package io.ubyte.tldr

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import io.ubyte.tldr.settings.SettingsSerializer
import io.ubyte.tldr.settings.SettingsStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

private const val TEST_DATASTORE_NAME: String = "test_datastore"
private val expectedSettings: Settings = Settings.getDefaultInstance()

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class SettingsStoreTest {
    private val context = ApplicationProvider.getApplicationContext<App>()
    private val coroutineDispatcher = StandardTestDispatcher()
    private val coroutineScope = TestScope(coroutineDispatcher + Job())
    private val dataStore: DataStore<Settings> =
        DataStoreFactory.create(
            serializer = SettingsSerializer,
            scope = coroutineScope,
            produceFile = { context.dataStoreFile(TEST_DATASTORE_NAME) }
        )

    private val settingsStore = SettingsStore(dataStore)

    @Test
    fun initialSettings() {
        coroutineScope.runTest {
            assertThat(settingsStore.getLastSync())
                .isEqualTo(expectedSettings.lastSync)
        }
    }

    @Test
    fun writeToDataStore() {
        coroutineScope.runTest {
            settingsStore.setLastSync(12345L)
            assertThat(settingsStore.getLastSync())
                .isEqualTo(12345L)
        }
    }

    @Before
    fun setup() {
        Dispatchers.setMain(coroutineDispatcher)
    }

    @After
    fun cleanUp() {
        runTest {
            context.dataStoreFile(TEST_DATASTORE_NAME).delete()
        }
        Dispatchers.resetMain()
        coroutineScope.cancel()
    }
}
