package kz.mentalmind.data.dto

data class CollectionDetailsDto(
    val id: Int,
    val name: String,
    val description: String,
    val type: String,
    val file_image: String,
    val for_feeling: String,
    val tags: List<String>,
    val meditations: List<MeditationDto>
)