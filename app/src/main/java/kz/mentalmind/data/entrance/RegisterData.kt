package kz.mentalmind.data.entrance

import com.google.gson.annotations.SerializedName

data class RegisterData(
    @SerializedName("data")
    val NewUserInfo: User,
    val error: String? = null
)

data class NewUserInfo(
    val birthday: String?,
    val city: String?,
    val country: String?,
    val date_joined: String?,
    val email: String?,
    val feeling: String?,
    val full_name: String?,
    val id: Int?,
    val is_active: Boolean?,
    val is_paid: Boolean?,
    val is_premium: Boolean?,
    val language: String?,
    val level: String?,
    val listened_minutes: Int?,
    val social_id: Int?,
    val social_login: String?,
    val subs_expiry_date: String?
)

data class PassRecoveryData(
    val success: Boolean
)