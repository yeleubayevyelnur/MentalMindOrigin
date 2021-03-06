package kz.mentalmind.data.api

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import kz.mentalmind.data.HelpResponse
import kz.mentalmind.data.Meditations
import kz.mentalmind.data.PromocodeResponse
import kz.mentalmind.data.dto.*
import kz.mentalmind.data.dto.Collection
import kz.mentalmind.data.entrance.LoginResponse
import kz.mentalmind.data.entrance.PassRecoveryData
import kz.mentalmind.data.entrance.RegisterData
import kz.mentalmind.data.entrance.SocialLoginRequest
import kz.mentalmind.data.profile.LevelDetailResponse
import kz.mentalmind.data.profile.LevelsResponse
import kz.mentalmind.data.profile.PassResetResponse
import kz.mentalmind.data.profile.ProfileResponse
import retrofit2.http.*

interface ApiService {
    //USER

    @FormUrlEncoded
    @POST("api/v2/users/register/")
    fun register(
        @Header("Accept-Language") language: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Observable<RegisterData>

    @FormUrlEncoded
    @POST("api/v2/users/login/")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Observable<LoginResponse>

    @POST("api/v2/users/social_login/")
    fun socialLogin(
        @Header("Accept-Language") language: String,
        @Body socialLoginRequest: SocialLoginRequest
    ): Single<LoginResponse>

    //User favorites

    @GET("api/v3/users/me/favorites/")
    fun getFavorites(
        @Header("Accept-Language") language: String,
        @Header("Authorization") token: String
    ): Single<CommonResponse<Pagination<FavoriteMeditation>>>

    @POST("api/v3/users/me/favorites/")
    fun addToFavorites(
        @Header("Authorization") language: String,
        @Body addToFavorites: AddToFavorites
    ): Completable

    @FormUrlEncoded
    @POST("api/v2/history/")
    fun sendUserHistory()

    @FormUrlEncoded
    @POST("api/v2/users/password_recovery/")
    fun passwordRecovery(
        @Field("email") email: String
    ): Single<PassRecoveryData>

    @FormUrlEncoded
    @PUT("api/v2/users/me/")
    fun updateUserInfo(
        @Field("full_name") fullName: String?,
        @Field("birthday") birthday: String?,
        @Field("country") country: String?,
        @Field("city") city: String?
    ): Observable<ProfileResponse>

    @FormUrlEncoded
    @PUT("api/v2/users/password_reset/")
    fun resetPassword(
        @Header("Authorization") token: String,
        @Field("old_password") oldPassword: String,
        @Field("new_password") newPassword: String
    ): Observable<PassResetResponse>

    @GET("api/v2/users/me/")
    fun getUserInfo(
        @Header("Accept-Language") language: String,
        @Header("Authorization") token: String
    ): Observable<ProfileResponse>

    @GET("api/2/users/me/subscription_status/")
    fun getSubscriptionStatus()

    @GET("api/v2/history/")
    fun getHistory(
        @Header("Authorization") token: String,
        @Query("date") date: String
    ): Observable<Meditations>

    /** Работа с ресурсами */
    @GET("api/v2/affirmations/")
    fun getAffirmations(
        @Header("Accept-Language") language: String,
        @Header("Authorization") token: String
    ): Single<CommonResponse<Pagination<Affirmation>>>

    @GET("api/v2/tags/")
    fun getTags(
        @Header("Accept-Language") language: String,
        @Header("Authorization") token: String
    ): Single<CommonResponse<Pagination<KeyValuePair>>>

    @GET("api/v2/challenges/")
    fun getChallenges(
        @Header("Accept-Language") language: String,
        @Header("Authorization") token: String
    ): Single<CommonResponse<Pagination<Challenge>>>

    @GET("api/v2/courses/")
    fun getCourses(
        @Header("Accept-Language") language: String,
        @Header("Authorization") token: String,
    ): Single<CommonResponse<Pagination<Course>>>

    @FormUrlEncoded
    @POST("api/v2/rating/")
    fun rateMeditation(
        @Field("star") star: Int,
        @Field("meditation") meditationId: Int
    )

    @FormUrlEncoded
    @POST("api/v2/promocode/")
    fun promocode(
        @Header("Authorization") token: String,
        @Field("promocode") promocode: String
    ): Observable<PromocodeResponse>

    @GET("api/v2/levels/")
    fun getLevels(
        @Header("Authorization") token: String
    ): Observable<LevelsResponse>

    @GET("api/v2/levels/{id}")
    fun getLevelDetail(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Observable<LevelDetailResponse>

    @FormUrlEncoded
    @POST("api/v2/help/")
    fun help(
        @Header("Authorization") token: String,
        @Field("text") text: String
    ): Observable<HelpResponse>


    //Collections

    @GET("api/v2/collections/")
    fun getCollections(
        @Header("Accept-Language") language: String,
        @Header("Authorization") token: String,
        @Query("type") type: Int,
        @Query("tags") tag: Int
    ): Observable<CommonResponse<Pagination<Collection>>>

    @GET("api/v2/collections/")
    fun getCollectionsByTypes(
        @Header("Accept-Language") language: String,
        @Header("Authorization") token: String,
        @Query("type") type: Int
    ): Observable<CommonResponse<Pagination<Collection>>>

    @GET("api/v2/collections/")
    fun getCollectionsByFeeling(
        @Header("Accept-Language") language: String,
        @Header("Authorization") token: String,
        @Query("type") type: Int = 1,
        @Query("for_feeling") feeling: Int
    ): Observable<CommonResponse<Pagination<Collection>>>

    @GET("api/v2/collection_types/")
    fun getCollectionTypes(
        @Header("Accept-Language") language: String,
        @Header("Authorization") token: String
    ): Single<CommonResponse<Pagination<KeyValuePair>>>

    @GET("api/v2/collections/{id}")
    fun getCollectionDetails(
        @Header("Accept-Language") language: String,
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Single<CommonResponse<CollectionDetails>>

    @GET("api/v2/challenges/{id}")
    fun getChallengeDetails(
        @Header("Accept-Language") language: String,
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Single<CommonResponse<ChallengeDetailsDto>>

    @POST("api/v2/rating/")
    fun setRating(
        @Header("Accept-Language") language: String,
        @Header("Authorization") token: String,
        @Body rateMeditation: RateMeditation
    ): Completable

    //tariffs
    @GET("api/v2/payments/tariffs/")
    fun getTariffs(
        @Header("Accept-Language") language: String,
        @Header("Authorization"
        ) token: String): Single<CommonResponse<TariffsResponse>>

    @POST("/api/v2/payments/android/init/")
    fun paymentInit(
        @Header("Accept-Language") language: String,
        @Header("Authorization") token: String,
        @Body paymentRequest: PaymentRequest
    ): Single<CommonResponse<PaymentResponse>>

    @GET("api/v2/online_listeners/")
    fun getOnlineListeners(
        @Header("Accept-Language") language: String,
        @Header("Authorization") token: String
    ): Single<CommonResponse<OnlineListenersResponse>>
}