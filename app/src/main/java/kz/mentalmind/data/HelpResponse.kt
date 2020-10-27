package kz.mentalmind.data

import com.google.gson.annotations.SerializedName

data class HelpResponse(
    @SerializedName("data")
    val helpData: HelpData,
    val error: String?
)

data class HelpData(
    val success: Boolean
)

data class PromocodeResponse(
    @SerializedName("data")
    val result: String,
    val error: String?
)