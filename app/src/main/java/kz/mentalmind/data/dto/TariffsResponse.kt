package kz.mentalmind.data.dto

data class TariffsResponse(
    val count: Int,
    val results: List<Tariff>
)