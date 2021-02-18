package kz.mentalmind.ui.main.instruments

import kz.mentalmind.data.dto.Collection

interface InstrumentClickListener {
    fun onInstrumentClicked(meditation: Collection)
}