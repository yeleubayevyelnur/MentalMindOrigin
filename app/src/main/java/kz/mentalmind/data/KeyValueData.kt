package kz.mentalmind.data


data class KeyValueData(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<KeyValuePair>
)

data class KeyValuePair(
    val id: Int,
    val name: String
)