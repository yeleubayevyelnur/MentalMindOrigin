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
        type: Int,
        tag: Int
    ): Observable<CollectionsResponse> {
        return apiService.getCollections("ru", "Token $token", type, tag)
            .subscribeOn(Schedulers.io())
    }

    fun getCourses(
        token: String,
    ): Single<CommonResponse<CoursesData>> {
        return apiService.getCourses("ru", "Token $token")
            .subscribeOn(Schedulers.io())
    }

    fun getCollectionsByTypes(
        token: String,
        type: Int
    ): Observable<CollectionsResponse> {
        return apiService.getCollectionsByTypes("ru", "Token $token", type)
            .subscribeOn(Schedulers.io())
    }

    fun getChallenges(token: String): Single<CommonResponse<ChallengesResponse>> {
        return apiService.getChallenges("ru", "Token $token")
            .subscribeOn(Schedulers.io())
    }

    fun getCollectionsByFeeling(
        token: String,
        feeling: Int
    ): Observable<CollectionsResponse> {
        return apiService.getCollectionsByFeeling(
            language = "ru",
            token = "Token $token",
            feeling = feeling
        )
            .subscribeOn(Schedulers.io())
    }

    fun getCollectionsTypes(
        token: String,
    ): Single<CommonResponse<KeyValueData>> {
        return apiService.getCollectionTypes("ru", "Token $token")
            .subscribeOn(Schedulers.io())
    }

    fun getTags(token: String): Single<CommonResponse<KeyValueData>> {
        return apiService.getTags("ru", "Token $token")
            .subscribeOn(Schedulers.io())
    }

    fun getProfile(token: String): Observable<ProfileResponse> {
        return apiService.getUserInfo("Token $token")
            .subscribeOn(Schedulers.io())
    }


    fun help(token: String, text: String): Observable<HelpResponse> {
        return apiService.help("Token $token", text)
            .subscribeOn(Schedulers.io())
    }

    fun promocode(token: String, promocode: String): Observable<PromocodeResponse> {
        return apiService.promocode("Token $token", promocode)
            .subscribeOn(Schedulers.io())
    }

    fun getLevels(token: String): Observable<LevelsResponse> {
        return apiService.getLevels("Token $token")
            .subscribeOn(Schedulers.io())
    }

    fun getLevelDetail(token: String, id: Int): Observable<LevelDetailResponse> {
        return apiService.getLevelDetail("Token $token", id)
            .subscribeOn(Schedulers.io())
    }

    fun getToken(): String? {
        return sPrefs.getString(Constants.TOKEN, null)
    }

    fun saveFeeling(id: Int) {
        sPrefs.edit().putInt(Constants.FEELING, id).apply()
    }

}