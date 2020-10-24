package kz.mentalmind.data.repository

import android.content.SharedPreferences
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kz.mentalmind.data.CollectionsResponse
import kz.mentalmind.data.TagsResponse
import kz.mentalmind.data.TagsResult
import kz.mentalmind.data.api.ApiService
import java.util.*

class MainRepository(
    private val apiService: ApiService,
    private var sPrefs: SharedPreferences,
    private val gson: Gson
) {
    fun getCollections(language: String): Observable<CollectionsResponse>{
        return apiService.getCollections(language)
            .subscribeOn(Schedulers.io())
    }

    fun getTags(language: String): Observable<TagsResponse>{
        return apiService.getTags(language)
            .subscribeOn(Schedulers.io())
    }
}