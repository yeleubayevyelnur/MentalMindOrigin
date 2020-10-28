package kz.mentalmind.data.repository

import android.content.SharedPreferences
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import kz.mentalmind.data.*
import kz.mentalmind.data.api.ApiService
import kz.mentalmind.data.entrance.User
import kz.mentalmind.data.profile.LevelDetailResponse
import kz.mentalmind.data.profile.LevelsResponse
import kz.mentalmind.data.profile.ProfileResponse
import kz.mentalmind.domain.dto.CoursesData
import kz.mentalmind.utils.Constants

class MainRepository(
    private val apiService: ApiService,
    private var sPrefs: SharedPreferences,
    private val gson: Gson
) {

    fun getUser(): User? {
        val userString = sPrefs.getString(Constants.USER, null)
        return gson.fromJson(userString, User::class.java)
    }

    fun getFeeling() = sPrefs.getInt(Constants.FEELING, 9999)

    fun getCollections(
        token: String,
        language: String,
        type: Int,
        tag: Int
    ): Observable<CollectionsResponse> {
        val accessToken = "Token $token"
        return apiService.getCollections(language, accessToken, type, tag)
            .subscribeOn(Schedulers.io())
    }

    fun getCourses(
        token: String,
        language: String
    ): Single<CommonResponse<CoursesData>> {
        val accessToken = "Token $token"
        return apiService.getCourses(language, accessToken)
            .subscribeOn(Schedulers.io())
    }

    fun getCollectionsByTypes(
        token: String,
        language: String,
        type: Int
    ): Observable<CollectionsResponse> {
        val accessToken = "Token $token"
        return apiService.getCollectionsByTypes(language, accessToken, type)
            .subscribeOn(Schedulers.io())
    }

    fun getCollectionsByFeeling(
        token: String,
        language: String,
        feeling: Int
    ): Observable<CollectionsResponse> {
        val accessToken = "Token $token"
        return apiService.getCollectionsByFeeling(
            language = language,
            token = accessToken,
            feeling = feeling
        )
            .subscribeOn(Schedulers.io())
    }

    fun getCollectionsTypes(
        token: String,
        language: String
    ): Single<KeyValueData> {
        val accessToken = "Token $token"
        return apiService.getCollectionTypes(language, accessToken)
            .subscribeOn(Schedulers.io())
    }

    fun getTags(token: String): Single<CommonResponse<KeyValueData>> {
        val accessToken = "Token $token"
        return apiService.getTags("ru", accessToken)
            .subscribeOn(Schedulers.io())
    }

    fun getProfile(token: String): Observable<ProfileResponse> {
        val accessToken = "Token $token"
        return apiService.getUserInfo(accessToken)
            .subscribeOn(Schedulers.io())
    }


    fun help(token: String, text: String): Observable<HelpResponse> {
        val accessToken = "Token $token"
        return apiService.help(accessToken, text)
            .subscribeOn(Schedulers.io())
    }

    fun promocode(token: String, promocode: String): Observable<PromocodeResponse> {
        val accessToken = "Token $token"
        return apiService.promocode(accessToken, promocode)
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

    fun getToken(): String? {
        return sPrefs.getString(Constants.TOKEN, null)
    }

    fun saveFeeling(id: Int) {
        sPrefs.edit().putInt(Constants.FEELING, id).apply()
    }

}