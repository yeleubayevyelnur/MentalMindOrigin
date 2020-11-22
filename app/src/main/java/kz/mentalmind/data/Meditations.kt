package kz.mentalmind.data

import com.google.gson.annotations.SerializedName

data class Meditations(
    @SerializedName("data")
    val meditationData: MeditationData,
    val error: String? = null
)

data class MeditationData(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: ArrayList<MeditationResult>
)

data class MeditationResult(
    val meditation: String
)