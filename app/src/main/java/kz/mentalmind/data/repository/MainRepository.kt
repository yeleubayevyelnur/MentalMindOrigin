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

    fun getCollections(token: String, language: String,type: Int, tag: Int): Observable<CollectionsResponse> {
        val accessToken = "Token $token"
        return apiService.getCollections(language, accessToken,type, tag)
            .subscribeOn(Schedulers.io())
    }

    fun getTags(language: String, token: String): Observable<TagsResponse> {
        val accessToken = "Token $token"
        return apiService.getTags(language, accessToken)
            .subscribeOn(Schedulers.io())
    }

    fun getProfile(token: String): Observable<ProfileResponse> {
        val accessToken = "Token $token"
        return apiService.getUserInfo(accessToken)
            .subscribeOn(Schedulers.io())
    }

    fun getLevels(token: String): Observable<LevelsResponse> {
        val accessToken = "Token $token"
        return apiService.getLevels(accessToken)
            .subscribeOn(Schedulers.io())
    }

    fun getLevelDetail(token: String, id: Int): Observable<LevelDetailResponse> {
        val accessToken = "Token $token"
        return apiService.getLevelDetail(accessToken, id)
            .subscribeOn(Schedulers.io())
    }

    fun getToken(context: Context): String? {
        sPrefs = context.getSharedPreferences(Constants.APP_PREF, Context.MODE_PRIVATE)
        return sPrefs.getString(Constants.TOKEN, null)
    }

}