package kz.mentalmind.ui.meditations

import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import kz.mentalmind.data.repository.MeditationRepository
import kz.mentalmind.data.dto.CollectionDetails

class MeditationsViewModel(private val meditationRepository: MeditationRepository) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private val collectionDetailsSubject = PublishSubject.create<CollectionDetails>()
    private val errorsSubject = PublishSubject.create<String>()


    fun getCollectionDetails(accessToken: String, id: Int) {
        val token = "Token $accessToken"
        compositeDisposable.add(
            meditationRepository.getCollectionDetails(token, id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    response.data?.let {
                        collectionDetailsSubject.onNext(it)
                    } ?: response.error?.let { errorsSubject.onNext(it) }
                }, {
                    errorsSubject.onNext(it.message ?: "")
                })
        )
    }

    fun observeCollectionDetails(): PublishSubject<CollectionDetails> {
        return collectionDetailsSubject
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    fun getToken(): String? {
        return meditationRepository.getToken()
    }
}