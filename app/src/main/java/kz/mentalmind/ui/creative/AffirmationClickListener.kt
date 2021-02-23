package kz.mentalmind.ui.creative

import kz.mentalmind.data.dto.Affirmation

interface AffirmationClickListener {
    fun onAffirmationClicked(affirmation: Affirmation)
}