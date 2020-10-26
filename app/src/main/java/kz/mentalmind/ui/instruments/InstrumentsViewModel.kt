package kz.mentalmind.ui.instruments

import android.content.Context
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import kz.mentalmind.data.Collections
import kz.mentalmind.data.TagsData
import kz.mentalmind.data.repository.MainRepository

class InstrumentsViewModel(private val mainRepository: MainRepository) : ViewModel() {
    private val tagsSubject = PublishSubject.create<TagsData>()
    private val instruments = PublishSubject.create<Collections>()
    private val errorsSubject = PublishSubject.create<String>()

    fun getTags(context: Context) {
        getToken(context)?.let {
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
        getToken(context)?.let {
            mainRepository.getCollections(it, "ru", 1, tag)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    if (response.error == null) {
                        instruments.onNext(response.data)
                    } else {
                        errorsSubject.onNext(response.error)
                    }
                }, {
                    errorsSubject.onNext(it.message ?: "")
                })
        }
    }

    fun observeTagsSubject(): PublishSubject<TagsData> {
        return tagsSubject
    }

    fun observeInstruments(): PublishSubject<Collections> {
        return instruments
    }

    fun getToken(context: Context): String? {
        return mainRepository.getToken(context)
    }
}