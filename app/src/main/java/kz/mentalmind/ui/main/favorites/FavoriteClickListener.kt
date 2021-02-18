package kz.mentalmind.ui.main.favorites

import kz.mentalmind.data.dto.FavoriteMeditation

interface FavoriteClickListener {
    fun onFavoriteClicked(meditation: FavoriteMeditation)
}