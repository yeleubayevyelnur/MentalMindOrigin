package kz.mentalmind.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import kz.mentalmind.data.CollectionsResponse
import kz.mentalmind.data.TagsResponse
import kz.mentalmind.data.TagsResult
import kz.mentalmind.data.repository.MainRepository

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {
    private val disposable = CompositeDisposable()
    private val errorsSubject = PublishSubject.create<String>()
    private val collectionsSubject = PublishSubject.create<CollectionsResponse>()
    private val tagsSubject = PublishSubject.create<TagsResponse>()

    fun getCollections(language: String){
        disposable.add(
            mainRepository.getCollections(language)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.error == null){
                        collectionsSubject.onNext(it)
                    } else {
                        errorsSubject.onNext(it.error)
                    }
                }, {
                    Log.e("error", it.message.toString())
                })
        )
    }

    fun getTags(language: String){
        disposable.add(
            mainRepository.getTags(language)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.error == null){
                        tagsSubject.onNext(it)
                    } else {
                        errorsSubject.onNext(it.error)
                    }
                }, {
                    Log.e("error", it.message.toString())
                })
        )
    }

    fun observeCollectionsSubject(): PublishSubject<CollectionsResponse> {
        return collectionsSubject
    }

    fun observeTagsSubject(): PublishSubject<TagsResponse> {
        return tagsSubject
    }

    fun observeErrorSubject(): Observable<String> {
        return errorsSubject
    }
}