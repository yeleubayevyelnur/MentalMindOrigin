package kz.mentalmind.data.repository

import android.content.SharedPreferences
import com.google.gson.Gson
import kz.mentalmind.data.api.ApiService

class AuthRepository(
    private val apiService: ApiService,
    private var sPrefs: SharedPreferences,
    private val gson: Gson
) {
}