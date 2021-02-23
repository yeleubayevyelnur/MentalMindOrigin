package kz.mentalmind.ui.home.challenges

import kz.mentalmind.data.dto.Challenge

interface ChallengeClickListener {
    fun onChallengeClicked(challenge: Challenge)
}