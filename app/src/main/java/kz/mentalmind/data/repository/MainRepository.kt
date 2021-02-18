package kz.mentalmind.data.repository

import android.content.SharedPreferences
import com.google.gson.Gson
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import kz.mentalmind.data.HelpResponse
import kz.mentalmind.data.Meditations
import kz.mentalmind.data.PromocodeResponse
import kz.mentalmind.data.api.ApiService
import kz.mentalmind.data.dto.*
import kz.mentalmind.data.dto.Collection
import kz.mentalmind.data.entrance.User
import kz.mentalmind.data.profile.LevelDetailResponse
import kz.mentalmind.data.profile.LevelsResponse
import kz.mentalmind.data.profile.PassResetResponse
import kz.mentalmind.data.profile.ProfileResponse
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
    ): Observable<CommonResponse<Pagination<Collection>>> {
        return apiService.getCollections("ru", "Token $token", type, tag)
            .subscribeOn(Schedulers.io())
    }

    fun getCourses(
        token: String,
    ): Single<CommonResponse<Pagination<Course>>> {
        return apiService.getCourses("ru", "Token $token")
            .subscribeOn(Schedulers.io())
    }

    fun getCollectionsByTypes(
        token: String,
        type: Int
    ): Observable<CommonResponse<Pagination<Collection>>> {
        return apiService.getCollectionsByTypes("ru", "Token $token", type)
            .subscribeOn(Schedulers.io())
    }

    fun getChallenges(token: String): Single<CommonResponse<Pagination<Challenge>>> {
        return apiService.getChallenges("ru", "Token $token")
            .subscribeOn(Schedulers.io())
    }

    fun getCollectionsByFeeling(
        token: String,
        feeling: Int
    ): Observable<CommonResponse<Pagination<Collection>>> {
        return apiService.getCollectionsByFeeling(
            language = "ru",
            token = "Token $token",
            feeling = feeling
        )
            .subscribeOn(Schedulers.io())
    }

    fun getCollectionsTypes(
        token: String,
    ): Single<CommonResponse<Pagination<KeyValuePair>>> {
        return apiService.getCollectionTypes("ru", "Token $token")
            .subscribeOn(Schedulers.io())
    }

    fun getTags(token: String): Single<CommonResponse<Pagination<KeyValuePair>>> {
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

    fun getHistory(token: String, date: String): Observable<Meditations> {
        val accessToken = "Token $token"
        return apiService.getHistory(accessToken, date)
            .subscribeOn(Schedulers.io())
    }

    fun getFavorites(token: String): Single<CommonResponse<Pagination<FavoriteMeditation>>> {
        return apiService.getFavorites("ru", "Token $token")
            .subscribeOn(Schedulers.io())
    }

    fun addToFavorites(token: String, addToFavorites: AddToFavorites): Completable {
        return apiService.addToFavorites("Token $token", addToFavorites)
            .subscribeOn(Schedulers.io())
    }

    fun getChallengeDetails(
        token: String,
        challengeId: Int
    ): Single<CommonResponse<ChallengeDetailsDto>> {
        return apiService.getChallengeDetails("ru", "Token $token", challengeId)
            .subscribeOn(Schedulers.io())
    }

    fun getAffirmations(token: String): Single<CommonResponse<Pagination<Affirmation>>> {
        return apiService.getAffirmations("ru", "Token $token")
            .subscribeOn(Schedulers.io())
    }

    fun passReset(
        token: String,
        oldPass: String,
        newPass: String
    ): Observable<PassResetResponse> {
        val accessToken = "Token $token"
        return apiService.resetPassword(accessToken, oldPass, newPass)
            .subscribeOn(Schedulers.io())
    }

    fun setRating(token: String, meditationId: Int, star: Int): Completable {
        return apiService.setRating("Token $token", RateMeditation(star, meditationId))
            .subscribeOn(Schedulers.io())
    }

    fun getTariffs(token: String): Single<CommonResponse<TariffsResponse>> {
        return apiService.getTariffs("Token $token")
            .subscribeOn(Schedulers.io())
    }
}