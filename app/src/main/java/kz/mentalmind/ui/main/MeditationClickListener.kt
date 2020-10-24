package kz.mentalmind.ui.main

import kz.mentalmind.data.CollectionItem

interface MeditationClickListener {
    fun onMeditationClicked(meditation: CollectionItem)
}