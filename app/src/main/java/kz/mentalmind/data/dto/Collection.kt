package kz.mentalmind.data.dto

data class Collection(
    val description: String,
    val file_image: String,
    val for_feeling: List<String>,
    val id: Int,
    val name: String,
    val tags: ArrayList<String>,
)