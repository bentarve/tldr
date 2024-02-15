package io.ubyte.tldr.model

data class PageIdentifier(
    val id: Long,
    val name: String,
    val platform: String
) {
    companion object {
        fun mapToPageIdentifier(id: Long, name: String, platform: String) =
            PageIdentifier(id, name, formatNames(platform))

        private fun formatNames(platform: String): String {
            return when (platform) {
                "linux" -> "Linux"
                "osx" -> "OSX"
                "sunos" -> "SunOS"
                "windows" -> "Windows"
                "android" -> "Android"
                "freebsd" -> "FreeBSD"
                "netbsd" -> "NetBSD"
                "openbsd" -> "OpenBSD"
                else -> platform
            }
        }
    }
}
