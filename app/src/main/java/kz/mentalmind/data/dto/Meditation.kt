package kz.mentalmind.data.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Meditation(
    val id: Int,
    val name: String,
    val description: String,
    val duration: Int,
    val file_male_voice: String?,
    val file_female_voice: String?
) : Parcelable