package kz.mentalmind.ui.main.challenges

import kz.mentalmind.data.Challenge

interface ChallengeClickListener {
    fun onChallengeClicked(challenge: Challenge)
}