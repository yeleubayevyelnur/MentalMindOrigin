package kz.mentalmind.ui.instruments

import android.content.Context
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import kz.mentalmind.data.dto.KeyValuePair
import kz.mentalmind.data.dto.Pagination
import kz.mentalmind.data.dto.Collection
import kz.mentalmind.data.repository.MainRepository

class InstrumentsViewModel(private val mainRepository: MainRepository) : ViewModel() {
    private val tagsSubject = PublishSubject.create<Pagination<KeyValuePair>>()
    private val instruments = PublishSubject.create<Pair<Int, Pagination<Collection>>>()
    private val errorsSubject = PublishSubject.create<String>()
    private val progressVisible = PublishSubject.create<Boolean>()

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
                    progressVisible.onNext(false)
                }, {
                    errorsSubject.onNext(it.message ?: "")
                    progressVisible.onNext(false)
                })
        }
    }

    fun observeTagsSubject(): PublishSubject<Pagination<KeyValuePair>> {
        return tagsSubject
    }

    fun observeInstruments(): PublishSubject<Pair<Int, Pagination<Collection>>> {
        return instruments
    }

    fun observeProgressVisibility(): Observable<Boolean> {
        return progressVisible
    }

    fun getToken(): String? {
        return mainRepository.getToken()
    }
}