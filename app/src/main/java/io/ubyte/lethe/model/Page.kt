package io.ubyte.lethe.model

data class Page(
    val name: String,
    val platform: String,
    val markdown: String
) {
    companion object {
        fun mapToPage(name: String, platform: String, markdown: String) =
            Page(name, platform, markdown)
    }
}
