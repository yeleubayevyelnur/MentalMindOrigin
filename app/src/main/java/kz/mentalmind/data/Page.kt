package kz.mentalmind.data

import android.graphics.drawable.Drawable

data class Page(
    val title: String,
    val id: Int,
    val description: String,
    val image: Drawable?
)