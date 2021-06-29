package kz.mentalmind.data.dto

import android.os.Parcelable

abstract class MeditationDto: Parcelable{
   abstract val id: Int
   abstract val name: String
   abstract val description: String
   abstract val duration: Int
   abstract val is_favorite: Boolean
   abstract val file_male_voice: String?
   abstract val file_female_voice: String?
}