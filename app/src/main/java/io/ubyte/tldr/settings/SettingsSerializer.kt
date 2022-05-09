package io.ubyte.tldr.settings

import androidx.datastore.core.Serializer
import io.ubyte.tldr.Settings
import java.io.InputStream
import java.io.OutputStream

@Suppress("BlockingMethodInNonBlockingContext")
object SettingsSerializer : Serializer<Settings> {
    override val defaultValue: Settings = Settings.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): Settings = Settings.parseFrom(input)

    override suspend fun writeTo(t: Settings, output: OutputStream) = t.writeTo(output)
}
