package kz.mentalmind.ui.purchase

import kz.mentalmind.data.dto.Tariff

interface TariffClickListener {
    fun onTariffClicked(tariff: Tariff)
}