package kz.mentalmind.ui.home.feelings

import kz.mentalmind.data.dto.Feeling

interface FeelingResultListener {
    fun onMoodClickedResult(feeling: Feeling)
}