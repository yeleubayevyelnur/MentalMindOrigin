package kz.mentalmind.ui.main.mood

import kz.mentalmind.data.Feeling

interface MoodResultListener {
    fun onMoodClickedResult(feeling: Feeling)
}