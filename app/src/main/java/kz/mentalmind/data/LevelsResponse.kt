package kz.mentalmind.data

import com.google.gson.annotations.SerializedName

data class LevelsResponse(
    @SerializedName("data")
    val levelsData: LevelsData,
    val error: String? = null
)

data class LevelsData(
    val count: Int,
    val next: Any,
    val previous: Any,
    val results: List<LevelsResult>
)

data class LevelsResult(
    val days_with_us: Int,
    val file_image: String,
    val id: Int,
    val label: String,
    val listened_minutes: Int,
    val name: String
)