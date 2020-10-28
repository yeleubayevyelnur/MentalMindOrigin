package kz.mentalmind.ui.instruments

import android.content.Context
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import kz.mentalmind.data.Collections
import kz.mentalmind.data.KeyValueData
import kz.mentalmind.data.repository.MainRepository

class InstrumentsViewModel(private val mainRepository: MainRepository) : ViewModel() {
    private val tagsSubject = PublishSubject.create<KeyValueData>()
    private val instruments = PublishSubject.create<Pair<Int, Collections>>()
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
            mainRepository.getCollections(it, "ru", 1, tag)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    if (response.error == null) {
                        instruments.onNext(Pair(tag, response.data))
                    } else {
                        errorsSubject.onNext(response.error)
                    }
                }, {
                    errorsSubject.onNext(it.message ?: "")
                })
        }
    }

    fun observeTagsSubject(): PublishSubject<KeyValueData> {
        return tagsSubject
    }

    fun observeInstruments(): PublishSubject<Pair<Int, Collections>> {
        return instruments
    }

    fun getToken(): String? {
        return mainRepository.getToken()
    }
}