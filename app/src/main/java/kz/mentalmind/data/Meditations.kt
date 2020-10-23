package kz.mentalmind.data

data class Meditations(
    val `data`: Data,
    val error: Any
)

data class Data(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Result>
)