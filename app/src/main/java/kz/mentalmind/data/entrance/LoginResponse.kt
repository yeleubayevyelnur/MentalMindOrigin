package kz.mentalmind.data.entrance

import kz.mentalmind.data.dto.Meditation
import kz.mentalmind.data.dto.Pagination

data class LoginResponse(
    val data: LoginData,
    val error: String?
)

data class LoginData(
    val access_token: String,
    val user: User
)

data class User(
    val id: Int,
    val email: String,
    val full_name: String,
    val date_joined: String,
    val birthday: String?,
    val country: String?,
    val city: String?,
    val subs_expiry_date: String?,
    val language: String,
    val is_premium: Boolean,
    val is_active: Boolean,
    val is_paid: Boolean,
    val level: String,
    val feeling: String?,
    val social_login: String?,
    val social_id: String?,
    val listened_minutes: Int,
    val days_with_us: Int,
    val favorite_meditations: List<Pagination<Meditation>>,
    val profile_image: String?,
    val tariff: UserTariff?
)

data class UserTariff(
    val apple_product_id: String,
    val id: Int,
    val name: String,
    val description: String,
    val price: Int
)