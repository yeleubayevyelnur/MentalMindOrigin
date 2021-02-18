package kz.mentalmind.data.dto

data class Challenge(
    val id: Int,
    val name: String,
    val description: String,
    val file_image: String,
    val date_begin: String,
    val date_end: String,
    val collections: List<String>
)