package kz.mentalmind.navigation

import androidx.annotation.IdRes
import kz.mentalmind.R

enum class NavigationTab(
    val fragmentTag: String,
    @IdRes val menuResId: Int
) {
    HOME(FragmentEnums.HOME, R.id.nav_home),
    INSTRUMENTS(FragmentEnums.INSTRUMENTS_FRAGMENT, R.id.nav_instruments),
    CREATIVE(FragmentEnums.CREATIVE_FRAGMENT, R.id.nav_creative),
    PROFILE(FragmentEnums.PROFILE_FRAGMENT, R.id.nav_profile);

    companion object {
        fun getByItemId(itemId: Int): NavigationTab {
            return values()
                .first { tab -> tab.menuResId == itemId }
        }
    }
}
