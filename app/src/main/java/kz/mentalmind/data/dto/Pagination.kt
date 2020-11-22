package kz.mentalmind.data.dto

data class Pagination<T>(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<T>
)