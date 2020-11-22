package kz.mentalmind.data.profile

import com.google.gson.annotations.SerializedName
import kz.mentalmind.data.HelpData

data class PassResetResponse(
    @SerializedName("data")
    val helpData: HelpData,
    val error: String? = null
)