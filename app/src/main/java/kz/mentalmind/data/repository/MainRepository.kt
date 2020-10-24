package kz.mentalmind.data.repository

import android.content.SharedPreferences
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kz.mentalmind.data.CollectionsResponse
import kz.mentalmind.data.TagsResponse
import kz.mentalmind.data.api.ApiService

class MainRepository(
    private val apiService: ApiService,
    private var sPrefs: SharedPreferences,
    private val gson: Gson
) {
    fun getCollections(language: String, type: Int, tag: Int): Observable<CollectionsResponse> {
        return apiService.getCollections(language, type, tag)
            .subscribeOn(Schedulers.io())
    }

    fun getTags(language: String): Observable<TagsResponse> {
        return apiService.getTags(language)
            .subscribeOn(Schedulers.io())
    }
}