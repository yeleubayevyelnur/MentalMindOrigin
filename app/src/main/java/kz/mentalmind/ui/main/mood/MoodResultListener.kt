package kz.mentalmind.ui.main.mood

import kz.mentalmind.data.MoodData

interface MoodResultListener {
    fun onMoodClickedResult(moodData: MoodData)
}