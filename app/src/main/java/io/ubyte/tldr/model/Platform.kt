package io.ubyte.tldr.model

enum class Platform {
    Common,
    Linux,
    OSX,
    SunOS,
    Windows,
    Android;

    companion object {
        fun formatNames(platform: String): String? {
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

        fun allPlatforms() = entries.toSet().toRegex()

        private fun Set<Platform>.toRegex(): Regex {
            return joinToString(separator = "|") { it.name }.toRegex(RegexOption.IGNORE_CASE)
        }
    }
}


