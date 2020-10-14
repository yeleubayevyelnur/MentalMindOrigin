package kz.mentalmind.data.api

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    /** Регистрация и Логин пользователя */
    @FormUrlEncoded
    @POST("users/register/")
    fun register()

    @FormUrlEncoded
    @POST("users/login/")
    fun login()

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
    fun passwordRecovery()

    @FormUrlEncoded
    @POST("users/me/")
    fun updateUserInfo()

    @FormUrlEncoded
    @POST("users/reset_password/")
    fun resetPassword()

    @GET("users/register/")
    fun userInfo()

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

    @GET("api/v1/collections/")
    fun getCollections(
        @Field("id") id: Int? = null
    )

    @GET("api/v1/tags/")
    fun getTags(
    )

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

    @GET("api/v1/collection_types/")
    fun getCollectionTypes()

    @FormUrlEncoded
    @POST("api/v1/help/")
    fun help(
        @Field("text") text: String
    )
}