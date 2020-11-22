package kz.mentalmind.ui.main

import kz.mentalmind.data.dto.CollectionDto

interface InstrumentClickListener {
    fun onInstrumentClicked(meditation: CollectionDto)
}