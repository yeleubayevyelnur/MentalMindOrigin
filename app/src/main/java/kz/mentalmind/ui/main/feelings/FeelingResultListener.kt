package kz.mentalmind.ui.main.feelings

import kz.mentalmind.data.dto.FeelingDto

interface FeelingResultListener {
    fun onMoodClickedResult(feeling: FeelingDto)
}