package kz.mentalmind.data.dto

data class FavoriteMeditation(
    val meditation_id: Int,
    val meditation_name: String,
    val meditation_description: String,
    val meditation_file_male_voice: String,
    val meditation_file_female_voice: String,
    val collection_id: Int,
    val file_image: String
)