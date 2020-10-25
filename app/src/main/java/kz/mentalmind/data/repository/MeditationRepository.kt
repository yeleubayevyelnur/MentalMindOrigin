package kz.mentalmind.data.repository

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import kz.mentalmind.data.CommonResponse
import kz.mentalmind.data.api.ApiService
import kz.mentalmind.domain.dto.CollectionDetailsDto

class MeditationRepository(
    private val apiService: ApiService,
) {
    fun getCollectionDetails(
        id: Int
    ): Single<CommonResponse<CollectionDetailsDto>> {
        return apiService.getCollectionDetails("ru", id)
            .subscribeOn(Schedulers.io())
    }
}