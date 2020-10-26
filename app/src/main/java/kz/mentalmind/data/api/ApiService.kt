package kz.mentalmind.data.api

import io.reactivex.Observable
import io.reactivex.Single
import kz.mentalmind.data.CollectionsResponse
import kz.mentalmind.data.CommonResponse
import kz.mentalmind.data.TagsData
import kz.mentalmind.data.entrance.LoginResponse
import kz.mentalmind.data.entrance.PassRecoveryData
import kz.mentalmind.data.entrance.RegisterData
import kz.mentalmind.data.profile.LevelDetailResponse
import kz.mentalmind.data.profile.LevelsResponse
import kz.mentalmind.data.profile.ProfileResponse
import kz.mentalmind.domain.dto.CollectionDetailsDto
import retrofit2.http.*

interface ApiService {
    /** Регистрация и Логин пользователя */
    @FormUrlEncoded
    @POST("users/register/")
    fun register(
        @Header("Accept-Language") language: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Observable<RegisterData>

    @FormUrlEncoded
    @POST("users/login/")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Observable<LoginResponse>

    @FormUrlEncoded
    @POST("users/social_login/")
    fun socialLogin()

    @FormUrlEncoded
    @POST("users/refresh_token/")
    fun refreshToken()

    @FormUrlEncoded
    @POST("api/v1/history/")
    fun sendUserHistory()

    @FormUrlEncoded
    @POST("users/password_recovery/")
    fun passwordRecovery(
        @Field("email") email: String
    ): Single<PassRecoveryData>

    @FormUrlEncoded
    @POST("users/me/")
    fun updateUserInfo()

    @FormUrlEncoded
    @PUT("users/reset_password/")
    fun resetPassword(
        @Field("old_password") oldPassword: String,
        @Field("new_password") newPassword: String
    )

    @GET("users/me/")
    fun getUserInfo(
        @Header("Authorization") token: String
    ): Observable<ProfileResponse>

    @GET("users/me/subscription_status/")
    fun getSubscriptionStatus()

    @GET("api/v1/history/")
    fun getHistory()


    /** Работа с ресурсами */
    @GET("api/v1/affirmations/")
    fun getAffirmation(
        @Field("id") id: Int? = null
    )

    @GET("api/v1/meditations/")
    fun getMeditations(
        @Field("id") id: Int? = null
    )

    @GET("api/v1/tags/")
    fun getTags(
        @Header("Accept-Language") language: String,
        @Header("Authorization") token: String
    ): Single<CommonResponse<TagsData>>

    @GET("api/v1/challenges/")
    fun getChallenges(
        @Field("id") id: Int? = null
    )

    @GET("api/v1/background_music/")
    fun getBackgroundMusic(
        @Field("id") id: Int? = null
    )

    @GET("api/v1/feelings/")
    fun getFeelings(
        @Field("id") id: Int? = null
    )

    @GET("api/v1/courses/")
    fun getCourses(
        @Field("id") id: Int? = null
    )

    @FormUrlEncoded
    @POST("api/v1/rating/")
    fun rateMeditation(
        @Field("star") star: Int,
        @Field("meditation") meditationId: Int
    )

    @FormUrlEncoded
    @POST("api/v1/promocode/")
    fun promocode(
        @Field("promocode") promocode: String
    )

    @GET("api/v1/levels/")
    fun getLevels(
        @Header("Authorization") token: String
    ): Observable<LevelsResponse>

    @GET("api/v1/levels/{id}")
    fun getLevelDetail(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Observable<LevelDetailResponse>

    @FormUrlEncoded
    @POST("api/v1/help/")
    fun help(
        @Field("text") text: String
    )


    //Collections

    @GET("api/v1/collections/")
    fun getCollections(
        @Header("Accept-Language") language: String,
        @Header("Authorization") token: String,
        @Query("type") type: Int,
        @Query("tags") tag: Int
    ): Observable<CollectionsResponse>


    @GET("api/v1/collection_types/")
    fun getCollectionTypes()

    @GET("api/v1/collections/{id}")
    fun getCollectionDetails(
        @Header("Accept-Language") language: String,
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Single<CommonResponse<CollectionDetailsDto>>

}