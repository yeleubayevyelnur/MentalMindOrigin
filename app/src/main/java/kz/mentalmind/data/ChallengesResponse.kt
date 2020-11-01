package kz.mentalmind.data

data class Challenge(
    val id: Int,
    val name: String,
    val description: String,
    val file_image: String,
    val date_begin: String,
    val date_end: String,
    val collections: List<String>
)

data class ChallengesResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Challenge>
)