package kz.mentalmind.data

import android.graphics.drawable.Drawable

data class Category (
    val name: String,
    val pages: ArrayList<Page>
)

data class Page(
    val title: String,
    val id: Int,
    val description: String,
    val image: Drawable?
)