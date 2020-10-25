package kz.mentalmind.data.profile

import com.google.gson.annotations.SerializedName

data class LevelDetailResponse(
    @SerializedName("data")
    val levelsDetailData: LevelDetailData,
    val error: String? = null
)

data class LevelDetailData(
    val days_with_us: Int,
    val file_image: String,
    val id: Int,
    val label: String,
    val listened_minutes: Int,
    val name: String
)