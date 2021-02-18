package kz.mentalmind.data.repository

import android.content.SharedPreferences
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import kz.mentalmind.data.dto.CommonResponse
import kz.mentalmind.data.api.ApiService
import kz.mentalmind.data.dto.CollectionDetails
import kz.mentalmind.utils.Constants

class MeditationRepository(
    private val apiService: ApiService,
    private var sPrefs: SharedPreferences
) {
    fun getCollectionDetails(
        token: String,
        id: Int
    ): Single<CommonResponse<CollectionDetails>> {
        return apiService.getCollectionDetails("ru", token, id)
            .subscribeOn(Schedulers.io())
    }

    fun getToken(): String? {
        return sPrefs.getString(Constants.TOKEN, null)
    }
}