package kz.mentalmind.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kz.mentalmind.data.CollectionsResponse
import kz.mentalmind.data.TagsResponse
import kz.mentalmind.data.api.ApiService
import kz.mentalmind.data.entrance.User
import kz.mentalmind.data.profile.LevelDetailResponse
import kz.mentalmind.data.profile.LevelsResponse
import kz.mentalmind.data.profile.ProfileResponse
import kz.mentalmind.utils.Constants

class MainRepository(
    private val apiService: ApiService,
    private var sPrefs: SharedPreferences,
    private val gson: Gson
) {

    fun getUser(context: Context): User? {
        sPrefs = context.getSharedPreferences(Constants.APP_PREF, Context.MODE_PRIVATE)
        val userString = sPrefs.getString(Constants.USER, null)
        return gson.fromJson(userString, User::class.java)
    }

    fun getCollections(language: String, type: Int, tag: Int): Observable<CollectionsResponse> {
        return apiService.getCollections(language, type, tag)
            .subscribeOn(Schedulers.io())
    }

    fun getTags(language: String): Observable<TagsResponse> {
        return apiService.getTags(language)
            .subscribeOn(Schedulers.io())
    }

    fun getProfile(authToken: String): Observable<ProfileResponse> {
        val token = "Token $authToken"
        return apiService.getUserInfo(token)
            .subscribeOn(Schedulers.io())
    }

    fun getLevels(): Observable<LevelsResponse> {
        return apiService.getLevels()
            .subscribeOn(Schedulers.io())
    }

    fun getLevelDetail(id: Int): Observable<LevelDetailResponse> {
        return apiService.getLevelDetail(id)
            .subscribeOn(Schedulers.io())
    }

    fun getToken(context: Context): String? {
        sPrefs = context.getSharedPreferences(Constants.APP_PREF, Context.MODE_PRIVATE)
        return sPrefs.getString(Constants.TOKEN, null)
    }

}