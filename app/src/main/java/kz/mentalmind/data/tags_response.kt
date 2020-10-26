package kz.mentalmind.data


data class TagsData(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<TagsResult>
)

data class TagsResult(
    val id: Int,
    val name: String
)