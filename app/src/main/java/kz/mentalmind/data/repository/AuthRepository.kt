package kz.mentalmind.data.repository

import android.content.SharedPreferences
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import kz.mentalmind.data.api.ApiService
import kz.mentalmind.data.entrance.*
import kz.mentalmind.utils.Constants

class AuthRepository(
    private val apiService: ApiService,
    private var sPrefs: SharedPreferences,
    private val gson: Gson
) {
    fun register(email: String, password: String, language: String): Observable<RegisterData> {
        return apiService.register(language, email, password)
            .subscribeOn(Schedulers.io())
    }

    fun passwordRecovery(email: String): Single<PassRecoveryData> {
        return apiService.passwordRecovery(email)
            .subscribeOn(Schedulers.io())
    }

    fun login(email: String, password: String): Observable<LoginResponse> {
        return apiService.login(email, password)
            .subscribeOn(Schedulers.io())
    }

    fun socialLogin(socialInfo: SocialInfo): Single<LoginResponse> {
        return apiService.socialLogin(SocialLoginRequest(socialInfo))
            .subscribeOn(Schedulers.io())
    }

    fun saveUser(user: User) {
        val userString: String = Gson().toJson(user)
        sPrefs.edit().putString(Constants.USER, userString).apply()
    }

    fun saveToken(accessToken: String) {
        sPrefs.edit().putString(Constants.TOKEN, accessToken).apply()
    }

    fun getToken(): String? {
        return sPrefs.getString(Constants.TOKEN, null)
    }

    fun getUser(): User? {
        val userString = sPrefs.getString(Constants.USER, null)
        return gson.fromJson(userString, User::class.java)
    }
}