package kz.mentalmind.data.entrance

import com.google.gson.annotations.SerializedName
import kz.mentalmind.data.Meditations

data class LoginResponse(
    @SerializedName("data")
    val loginData: LoginData,
    val error: String?
)

data class LoginData(
    val access_token: String,
    val user: User
)

data class User(
    val birthday: String?,
    val city: String?,
    val country: String?,
    val date_joined: String,
    val email: String,
    val favorite_meditations: List<Meditations>,
    val feeling: String?,
    val full_name: String,
    val id: Int,
    val is_active: Boolean,
    val is_paid: Boolean,
    val is_premium: Boolean,
    val language: String,
    val level: String,
    val listened_minutes: Int,
    val social_id: Int?,
    val social_login: Int?,
    val subs_expiry_date: String?
)