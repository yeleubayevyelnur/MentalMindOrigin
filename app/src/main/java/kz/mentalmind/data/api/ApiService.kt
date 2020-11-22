package kz.mentalmind.data.api

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import kz.mentalmind.data.HelpResponse
import kz.mentalmind.data.Meditations
import kz.mentalmind.data.PromocodeResponse
import kz.mentalmind.data.dto.*
import kz.mentalmind.data.entrance.LoginResponse
import kz.mentalmind.data.entrance.PassRecoveryData
import kz.mentalmind.data.entrance.RegisterData
import kz.mentalmind.data.entrance.SocialLoginRequest
import kz.mentalmind.data.profile.LevelDetailResponse
import kz.mentalmind.data.profile.LevelsResponse
import kz.mentalmind.data.profile.ProfileResponse
import retrofit2.http.*

interface ApiService {
    //USER

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

    @POST("users/social_login/")
    fun socialLogin(@Body socialLoginRequest: SocialLoginRequest): Single<LoginResponse>

    //User favorites

    @GET("users/me/favorites/")
    fun getFavorites(
        @Header("Accept-Language") language: String,
        @Header("Authorization") token: String
    ): Single<CommonResponse<Pagination<FavoriteMeditationDto>>>

    @POST("users/me/favorites/")
    fun addToFavorites(
        @Header("Authorization") language: String,
        @Body addToFavorites: AddToFavorites
    ): Completable

    @FormUrlEncoded
    @POST("api/v1/history/")
    fun sendUserHistory()

    @FormUrlEncoded
    @POST("users/password_recovery/")
    fun passwordRecovery(
        @Field("email") email: String
    ): Single<PassRecoveryData>

    @FormUrlEncoded
    @PUT("users/me/")
    fun updateUserInfo(
        @Field("full_name") fullName: String?,
        @Field("birthday") birthday: String?,
        @Field("country") country: String?,
        @Field("city") city: String?
    ): Observable<ProfileResponse>

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
    fun getHistory(
        @Header("Authorization") token: String,
        @Query("date") date: String
    ): Observable<Meditations>

    /** Работа с ресурсами */
    @GET("api/v1/affirmations/")
    fun getAffirmations(
        @Header("Accept-Language") language: String,
        @Header("Authorization") token: String
    ): Single<CommonResponse<Pagination<Affirmation>>>

    @GET("api/v1/tags/")
    fun getTags(
        @Header("Accept-Language") language: String,
        @Header("Authorization") token: String
    ): Single<CommonResponse<Pagination<KeyValuePairDto>>>

    @GET("api/v1/challenges/")
    fun getChallenges(
        @Header("Accept-Language") language: String,
        @Header("Authorization") token: String
    ): Single<CommonResponse<Pagination<ChallengeDto>>>

    @GET("api/v1/courses/")
    fun getCourses(
        @Header("Accept-Language") language: String,
        @Header("Authorization") token: String,
    ): Single<CommonResponse<Pagination<CourseDto>>>

    @FormUrlEncoded
    @POST("api/v1/rating/")
    fun rateMeditation(
        @Field("star") star: Int,
        @Field("meditation") meditationId: Int
    )

    @FormUrlEncoded
    @POST("api/v1/promocode/")
    fun promocode(
        @Header("Authorization") token: String,
        @Field("promocode") promocode: String
    ): Observable<PromocodeResponse>

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
        @Header("Authorization") token: String,
        @Field("text") text: String
    ): Observable<HelpResponse>


    //Collections

    @GET("api/v1/collections/")
    fun getCollections(
        @Header("Accept-Language") language: String,
        @Header("Authorization") token: String,
        @Query("type") type: Int,
        @Query("tags") tag: Int
    ): Observable<CommonResponse<Pagination<CollectionDto>>>

    @GET("api/v1/collections/")
    fun getCollectionsByTypes(
        @Header("Accept-Language") language: String,
        @Header("Authorization") token: String,
        @Query("type") type: Int
    ): Observable<CommonResponse<Pagination<CollectionDto>>>

    @GET("api/v1/collections/")
    fun getCollectionsByFeeling(
        @Header("Accept-Language") language: String,
        @Header("Authorization") token: String,
        @Query("type") type: Int = 1,
        @Query("for_feeling") feeling: Int
    ): Observable<CommonResponse<Pagination<CollectionDto>>>

    @GET("api/v1/collection_types/")
    fun getCollectionTypes(
        @Header("Accept-Language") language: String,
        @Header("Authorization") token: String
    ): Single<CommonResponse<Pagination<KeyValuePairDto>>>

    @GET("api/v1/collections/{id}")
    fun getCollectionDetails(
        @Header("Accept-Language") language: String,
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Single<CommonResponse<CollectionDetailsDto>>

    @GET("api/v1/challenges/{id}")
    fun getChallengeDetails(
        @Header("Accept-Language") language: String,
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Single<CommonResponse<ChallengeDetailsDto>>

}