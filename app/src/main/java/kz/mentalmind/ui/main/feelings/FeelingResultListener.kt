package kz.mentalmind.ui.main.feelings

import kz.mentalmind.data.Feeling

interface FeelingResultListener {
    fun onMoodClickedResult(feeling: Feeling)
}