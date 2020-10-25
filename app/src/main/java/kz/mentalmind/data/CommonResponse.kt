package kz.mentalmind.data

data class CommonResponse<T>(
    val data: T?,
    val error: String?
)