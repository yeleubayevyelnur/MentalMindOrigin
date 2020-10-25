package kz.mentalmind.data.profile

import com.google.gson.annotations.SerializedName

data class LevelsResponse(
    @SerializedName("data")
    val levelsData: LevelsData,
    val error: String? = null
)

data class LevelsData(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: ArrayList<LevelsResult>
)

data class LevelsResult(
    val days_with_us: Int,
    val file_image: String,
    val id: Int,
    val label: String,
    val listened_minutes: Int,
    val name: String
)