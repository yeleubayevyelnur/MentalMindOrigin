package kz.mentalmind.ui.main.challenges

import kz.mentalmind.data.dto.Challenge

interface ChallengeClickListener {
    fun onChallengeClicked(challenge: Challenge)
}