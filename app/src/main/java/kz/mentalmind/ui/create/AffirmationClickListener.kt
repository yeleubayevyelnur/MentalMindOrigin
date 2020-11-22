package kz.mentalmind.ui.create

import kz.mentalmind.data.dto.Affirmation

interface AffirmationClickListener {
    fun onAffirmationClicked(affirmation: Affirmation)
}