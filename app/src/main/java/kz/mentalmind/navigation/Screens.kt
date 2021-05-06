package kz.mentalmind.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import kz.mentalmind.ui.creative.CreativeFragment
import kz.mentalmind.ui.home.HomeFragment
import kz.mentalmind.ui.home.challenges.ChallengeInstrumentsFragment
import kz.mentalmind.ui.instruments.InstrumentsFragment
import kz.mentalmind.ui.meditations.MeditationsFragment
import kz.mentalmind.ui.profile.ProfileFragment

object Screens {
    fun main() = FragmentScreen { HomeFragment() }
    fun instruments() = FragmentScreen { InstrumentsFragment() }
    fun creation() = FragmentScreen { CreativeFragment() }
    fun profile() = FragmentScreen { ProfileFragment() }
    fun meditationsFragment(id: Int) = FragmentScreen { MeditationsFragment.newInstance(id) }
    fun challengeInstrumentsFragment(id: Int) =
        FragmentScreen { ChallengeInstrumentsFragment.newInstance(id) }
}