package io.ubyte.lethe.model

enum class Platform {
    Common,
    Linux,
    OSX,
    SunOS,
    Windows,
    Android;

    companion object {
        fun allPlatforms() = values().toSet().toRegex()

        fun formatFromLowercase(platform: String): String? {
            return when (platform) {
                Common.name.lowercase() -> Common.name
                Linux.name.lowercase() -> Linux.name
                OSX.name.lowercase() -> OSX.name
                SunOS.name.lowercase() -> SunOS.name
                Windows.name.lowercase() -> Windows.name
                Android.name.lowercase() -> Android.name
                else -> null
            }
        }
    }
}

fun Set<Platform>.toRegex(): Regex {
    return joinToString(separator = "|") { it.name }.toRegex(RegexOption.IGNORE_CASE)
}
