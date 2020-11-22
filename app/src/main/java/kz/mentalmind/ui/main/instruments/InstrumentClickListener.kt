package kz.mentalmind.ui.main.instruments

import kz.mentalmind.data.dto.CollectionDto

interface InstrumentClickListener {
    fun onInstrumentClicked(meditation: CollectionDto)
}