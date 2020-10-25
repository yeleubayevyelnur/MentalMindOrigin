package kz.mentalmind.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import kz.mentalmind.data.api.ApiService
import kz.mentalmind.data.entrance.LoginResponse
import kz.mentalmind.data.entrance.PassRecoveryData
import kz.mentalmind.data.entrance.RegisterData
import kz.mentalmind.data.entrance.User
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

    fun saveUser(user: User, context: Context) {
        sPrefs = context.getSharedPreferences(Constants.APP_PREF, Context.MODE_PRIVATE)
        val userString: String = Gson().toJson(user)
        val editor: SharedPreferences.Editor = sPrefs.edit()
        editor.putString(Constants.USER, userString).apply()
    }

    fun saveToken(context: Context, accessToken:String) {
        sPrefs = context.getSharedPreferences(Constants.APP_PREF, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sPrefs.edit()
        editor.putString(Constants.TOKEN, accessToken).apply()
    }

    fun getToken(context: Context): String? {
        sPrefs = context.getSharedPreferences(Constants.APP_PREF, Context.MODE_PRIVATE)
        return sPrefs.getString(Constants.TOKEN, null)
    }

    fun getUser(context: Context): User? {
        sPrefs = context.getSharedPreferences(Constants.APP_PREF, Context.MODE_PRIVATE)
        val userString = sPrefs.getString(Constants.USER, null)
        return gson.fromJson(userString, User::class.java)
    }
}