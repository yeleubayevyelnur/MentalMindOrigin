package kz.mentalmind.ui.meditations

import kz.mentalmind.data.dto.Meditation

interface MeditationClickListener {
    fun onMeditationClicked(meditation: Meditation)
}