package kz.mentalmind.data.dto

data class CommonResponse<T>(
    val data: T?,
    val error: String?
)