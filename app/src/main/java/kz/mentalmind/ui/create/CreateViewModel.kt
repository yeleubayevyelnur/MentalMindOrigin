package kz.mentalmind.ui.create

import android.content.Context
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import kz.mentalmind.data.dto.KeyValuePairDto
import kz.mentalmind.data.dto.Pagination
import kz.mentalmind.data.dto.CollectionDto
import kz.mentalmind.data.repository.MainRepository

class CreateViewModel(private val mainRepository: MainRepository) : ViewModel() {
    private val collectionTypes = PublishSubject.create<Pagination<KeyValuePairDto>>()
    private val instruments = PublishSubject.create<Pair<Int, Pagination<CollectionDto>>>()
    private val errorsSubject = PublishSubject.create<String>()

    fun getCollectionTypes(context: Context) {
        getToken()?.let {
            mainRepository.getCollectionsTypes(it)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    if (response.error == null) {
                        if (response.data != null) {
                            collectionTypes.onNext(response.data)
                        }
                    } else {
                        errorsSubject.onNext(response.error)
                    }

                }, {
                    errorsSubject.onNext(it.message ?: "")
                })
        }
    }

    fun getCollectionsByTypes(context: Context, type: Int) {
        getToken()?.let {
            mainRepository.getCollectionsByTypes(it, type)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    if (response.error == null) {
                        if (response.data != null) {
                            instruments.onNext(Pair(type, response.data))
                        }
                    } else {
                        errorsSubject.onNext(response.error)
                    }
                }, {
                    errorsSubject.onNext(it.message ?: "")
                })
        }
    }

    fun observeCollectionTypesSubject(): PublishSubject<Pagination<KeyValuePairDto>> {
        return collectionTypes
    }

    fun observeInstruments(): PublishSubject<Pair<Int, Pagination<CollectionDto>>> {
        return instruments
    }

    fun getToken(): String? {
        return mainRepository.getToken()
    }
}