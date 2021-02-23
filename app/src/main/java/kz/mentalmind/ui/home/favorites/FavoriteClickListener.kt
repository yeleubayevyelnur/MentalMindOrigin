package kz.mentalmind.ui.home.favorites

import kz.mentalmind.data.dto.FavoriteMeditation

interface FavoriteClickListener {
    fun onFavoriteClicked(meditation: FavoriteMeditation)
}