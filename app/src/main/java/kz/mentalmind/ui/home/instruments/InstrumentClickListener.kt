package kz.mentalmind.ui.home.instruments

import kz.mentalmind.data.dto.Collection

interface InstrumentClickListener {
    fun onInstrumentClicked(meditation: Collection)
}