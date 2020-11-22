package kz.mentalmind.ui.meditations

import kz.mentalmind.data.dto.MeditationDto

interface MeditationClickListener {
    fun onMeditationClicked(meditation: MeditationDto)
}