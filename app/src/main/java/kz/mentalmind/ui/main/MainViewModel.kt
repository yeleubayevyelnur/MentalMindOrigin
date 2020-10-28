package kz.mentalmind.ui.main

import android.content.Context
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import kz.mentalmind.data.CollectionsResponse
import kz.mentalmind.data.repository.MainRepository

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {
    private val disposable = CompositeDisposable()
    private val errorsSubject = PublishSubject.create<String>()
    private val streamOfLifeSubject = PublishSubject.create<CollectionsResponse>()
    private val instrumentsForFeeling = PublishSubject.create<CollectionsResponse>()

    fun getStreamOfLife(token: String) {
        disposable.add(
            mainRepository.getCollections(token, "ru", 1, 1)
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

    fun getCollectionsByFeeling(token: String, feeling: Int) {
        disposable.add(
            mainRepository.getCollectionsByFeeling(token, "ru", feeling)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.error == null) {
                        instrumentsForFeeling.onNext(it)
                    } else {
                        errorsSubject.onNext(it.error)
                    }
                }, {
                    errorsSubject.onNext(it.message ?: "")
                })
        )
    }

    fun observeStreamOfLife(): PublishSubject<CollectionsResponse> {
        return streamOfLifeSubject
    }

    fun observeInstrumentsForFeeling(): PublishSubject<CollectionsResponse> {
        return instrumentsForFeeling
    }

    fun observeErrorSubject(): Observable<String> {
        return errorsSubject
    }

    fun getToken(context: Context): String? {
        return mainRepository.getToken(context)
    }
}