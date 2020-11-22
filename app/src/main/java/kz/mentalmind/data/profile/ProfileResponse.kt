package kz.mentalmind.data.profile

import com.google.gson.annotations.SerializedName
import kz.mentalmind.data.dto.MeditationDto

data class ProfileResponse(
    @SerializedName("data")
    val profileData: ProfileData,
    val error: String? = null
)

data class ProfileData(
    val birthday: String?,
    val city: String?,
    val country: String?,
    val date_joined: String?,
    val days_with_us: Int,
    val email: String,
    val favorite_meditations: List<MeditationDto>,
    val feeling: Int?,
    val full_name: String,
    val id: Int,
    val is_active: Boolean,
    val is_paid: Boolean,
    val is_premium: Boolean,
    val language: String,
    val level: Int,
    val listened_minutes: Int,
    val subs_expiry_date: String?,
    val profile_image: String?
)