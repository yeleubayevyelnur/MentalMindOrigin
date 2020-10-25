package kz.mentalmind.ui.main

import android.content.Context
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import kz.mentalmind.data.CollectionsResponse
import kz.mentalmind.data.TagsResponse
import kz.mentalmind.data.repository.MainRepository

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {
    private val disposable = CompositeDisposable()
    private val errorsSubject = PublishSubject.create<String>()
    private val collectionsSubject = PublishSubject.create<CollectionsResponse>()
    private val tagsSubject = PublishSubject.create<TagsResponse>()
    private val streamOfLifeSubject = PublishSubject.create<CollectionsResponse>()

//    fun getCollections(language: String) {
//        disposable.add(
//            mainRepository.getCollections(language)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                    if (it.error == null) {
//                        collectionsSubject.onNext(it)
//                    } else {
//                        errorsSubject.onNext(it.error)
//                    }
//                }, {
//                    Log.e("error", it.message.toString())
//                })
//        )
//    }

    fun getStreamOfLife(token: String, language: String) {
        disposable.add(
            mainRepository.getCollections(token, language, 1, 1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.error == null) {
                        streamOfLifeSubject.onNext(it)
                    } else {
                        errorsSubject.onNext(it.error)
                    }
                }, {
                    errorsSubject.onNext(it.message ?: "")
                })
        )
    }

    fun observeCollectionsSubject(): PublishSubject<CollectionsResponse> {
        return collectionsSubject
    }

    fun observeStreamOfLife(): PublishSubject<CollectionsResponse> {
        return streamOfLifeSubject
    }

    fun observeTagsSubject(): PublishSubject<TagsResponse> {
        return tagsSubject
    }

    fun observeErrorSubject(): Observable<String> {
        return errorsSubject
    }

    fun getToken(context: Context): String? {
        return mainRepository.getToken(context)
    }
}