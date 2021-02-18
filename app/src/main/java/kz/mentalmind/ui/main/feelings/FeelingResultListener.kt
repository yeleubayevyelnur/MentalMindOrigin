package kz.mentalmind.ui.main.feelings

import kz.mentalmind.data.dto.Feeling

interface FeelingResultListener {
    fun onMoodClickedResult(feeling: Feeling)
}