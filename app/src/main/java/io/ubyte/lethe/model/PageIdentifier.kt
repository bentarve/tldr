package io.ubyte.lethe.model

data class PageIdentifier(
    val id: Long,
    val name: String,
    val platform: String
) {
    companion object {
        fun mapToPageIdentifier(id: Long, name: String, platform: String) =
            PageIdentifier(id, name, platform)
    }
}
