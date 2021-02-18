package kz.mentalmind.data.dto

data class Tariff(
    val id: Int,
    val name: String,
    val description: String,
    val price: Long
)


data class TariffsResponseDto(
    val count: Int,
    val results: List<Tariff>
)