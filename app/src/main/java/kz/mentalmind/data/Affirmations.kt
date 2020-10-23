package kz.mentalmind.data

import com.google.gson.annotations.SerializedName

data class Affirmations(
    @SerializedName("data")
    val affirmationsData: AffirmationsData,
    val error: String?
)

data class AffirmationsData(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Result>
)

data class Result(
    val date: String,
    val description: String,
    val file_image: String,
    val id: Int,
    val name: String
)