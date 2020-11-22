package kz.mentalmind.data.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Affirmation(
    val id: Int,
    val name: String,
    val description: String,
    val file_image: String,
    val date: String
) : Parcelable