package kz.mentalmind.data.dto

data class CollectionDto(
    val description: String,
    val file_image: String,
    val for_feeling: String,
    val id: Int,
    val name: String,
    val tags: ArrayList<String>,
    val type: String
)