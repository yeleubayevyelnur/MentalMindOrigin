package kz.mentalmind.ui.instruments

import android.content.Context
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import kz.mentalmind.data.dto.KeyValuePairDto
import kz.mentalmind.data.dto.Pagination
import kz.mentalmind.data.dto.CollectionDto
import kz.mentalmind.data.repository.MainRepository

class InstrumentsViewModel(private val mainRepository: MainRepository) : ViewModel() {
    private val tagsSubject = PublishSubject.create<Pagination<KeyValuePairDto>>()
    private val instruments = PublishSubject.create<Pair<Int, Pagination<CollectionDto>>>()
    private val errorsSubject = PublishSubject.create<String>()

    fun getTags(context: Context) {
        getToken()?.let {
            mainRepository.getTags(it)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    if (response.error == null) {
                        if (response.data != null) {
                            tagsSubject.onNext(response.data)
                        }
                    } else {
                        errorsSubject.onNext(response.error)
                    }
                }, {})
        }
    }

    fun getCollectionsByTags(context: Context, tag: Int) {
        getToken()?.let {
            mainRepository.getCollections(it, 1, tag)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    if (response.error == null) {
                        if (response.data != null) {
                            instruments.onNext(Pair(tag, response.data))
                        }
                    } else {
                        errorsSubject.onNext(response.error)
                    }
                }, {
                    errorsSubject.onNext(it.message ?: "")
                })
        }
    }

    fun observeTagsSubject(): PublishSubject<Pagination<KeyValuePairDto>> {
        return tagsSubject
    }

    fun observeInstruments(): PublishSubject<Pair<Int, Pagination<CollectionDto>>> {
        return instruments
    }

    fun getToken(): String? {
        return mainRepository.getToken()
    }
}