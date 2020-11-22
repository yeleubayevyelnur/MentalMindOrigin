package kz.mentalmind.ui.main.favorites

import kz.mentalmind.data.dto.FavoriteMeditationDto

interface FavoriteClickListener {
    fun onFavoriteClicked(meditation: FavoriteMeditationDto)
}