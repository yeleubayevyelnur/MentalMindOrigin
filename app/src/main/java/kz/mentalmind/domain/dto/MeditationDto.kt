package kz.mentalmind.domain.dto

data class MeditationDto(
    val id: Int,
    val name: String,
    val description: String,
    val duration: Int,
    val file_male_voice: String,
    val file_female_voice: String
)