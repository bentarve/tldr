package io.ubyte.lethe.pages.model

import java.util.*

enum class Platform(val flag: Int) {
    COMMON(1),
    LINUX(2),
    OSX(4),
    SUNOS(8),
    WINDOWS(16),
    ANDROID(32);

    companion object {
        fun all() = values().toSet().toRegex()
    }
}

fun Set<Platform>.toRegex() = joinToString("|") { platform ->
    platform.name.lowercase(Locale.ROOT)
}.toRegex()
