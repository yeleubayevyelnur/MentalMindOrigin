package kz.mentalmind.ui.meditations

import kz.mentalmind.domain.dto.MeditationDto

interface MeditationClickListener {
    fun onMeditationClicked(meditation: MeditationDto)
}