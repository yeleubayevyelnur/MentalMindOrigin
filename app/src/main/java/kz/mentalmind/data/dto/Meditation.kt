package kz.mentalmind.data.dto

import kotlinx.android.parcel.Parcelize

@Parcelize
data class Meditation(
    override val id: Int,
    override val name: String,
    override val description: String,
    override val duration: Int,
    override val is_favorite: Boolean,
    override val file_male_voice: String?,
    override val file_female_voice: String?
) : MeditationDto()