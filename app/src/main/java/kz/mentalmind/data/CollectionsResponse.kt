package kz.mentalmind.data

data class CollectionsResponse(
    val data: Collections,
    val error: String?
)

data class Collections(
    val count: Int,
    val next: String,
    val previous: String?,
    val results: ArrayList<CollectionItem>
)

data class CollectionItem(
    val description: String,
    val file_image: String,
    val for_feeling: String,
    val id: Int,
    val name: String,
    val tags: ArrayList<String>,
    val type: String
)