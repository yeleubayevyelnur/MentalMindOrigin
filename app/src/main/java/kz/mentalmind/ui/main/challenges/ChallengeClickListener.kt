package kz.mentalmind.ui.main.challenges

import kz.mentalmind.data.dto.ChallengeDto

interface ChallengeClickListener {
    fun onChallengeClicked(challenge: ChallengeDto)
}