package kz.mentalmind.data.repository

import android.content.Context
import android.content.SharedPreferences
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import kz.mentalmind.data.CommonResponse
import kz.mentalmind.data.api.ApiService
import kz.mentalmind.domain.dto.CollectionDetailsDto
import kz.mentalmind.utils.Constants

class MeditationRepository(
    private val apiService: ApiService,
    private var sPrefs: SharedPreferences
) {
    fun getCollectionDetails(
        token: String,
        id: Int
    ): Single<CommonResponse<CollectionDetailsDto>> {
        return apiService.getCollectionDetails("ru", token, id)
            .subscribeOn(Schedulers.io())
    }

    fun getToken(context: Context): String? {
        sPrefs = context.getSharedPreferences(Constants.APP_PREF, Context.MODE_PRIVATE)
        return sPrefs.getString(Constants.TOKEN, null)
    }
}