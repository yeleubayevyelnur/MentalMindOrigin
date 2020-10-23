package kz.mentalmind.data.repository

import android.content.SharedPreferences
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import kz.mentalmind.data.api.ApiService
import kz.mentalmind.data.entrance.LoginResponse
import kz.mentalmind.data.entrance.PassRecoveryData
import kz.mentalmind.data.entrance.RegisterData

class AuthRepository(
    private val apiService: ApiService,
    private var sPrefs: SharedPreferences,
    private val gson: Gson
) {

    private val USER = "user"

    fun register(email: String, password: String, language: String): Observable<RegisterData> {
        return apiService.register(language, email, password)
            .subscribeOn(Schedulers.io())
    }

    fun passwordRecovery(email: String): Single<PassRecoveryData> {
        return apiService.resetPassword(email)
            .subscribeOn(Schedulers.io())
    }

    fun login(email: String, password: String): Observable<LoginResponse> {
        return apiService.login(email, password)
            .subscribeOn(Schedulers.io())
    }
}