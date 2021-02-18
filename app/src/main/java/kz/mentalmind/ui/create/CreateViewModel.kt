package kz.mentalmind.ui.create

import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import kz.mentalmind.data.dto.Affirmation
import kz.mentalmind.data.dto.Collection
import kz.mentalmind.data.dto.KeyValuePair
import kz.mentalmind.data.dto.Pagination
import kz.mentalmind.data.repository.MainRepository

class CreateViewModel(private val mainRepository: MainRepository) : ViewModel() {
    private val collectionTypes = PublishSubject.create<Pagination<KeyValuePair>>()
    private val instruments = PublishSubject.create<Pair<Int, Pagination<Collection>>>()
    private val affirmations = PublishSubject.create<Pagination<Affirmation>>()
    private val errorsSubject = PublishSubject.create<String>()

    fun getCollectionTypes() {
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

    fun getCollectionsByTypes(type: Int) {
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

    fun getAffirmations() {
        getToken()?.let {
            mainRepository.getAffirmations(it)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    if (response.error == null) {
                        if (response.data != null) {
                            affirmations.onNext(response.data)
                        }
                    } else {
                        errorsSubject.onNext(response.error)
                    }
                }, {
                    errorsSubject.onNext(it.message ?: "")
                })
        }
    }

    fun observeAffirmations(): PublishSubject<Pagination<Affirmation>> {
        return affirmations
    }

    fun observeCollectionTypesSubject(): PublishSubject<Pagination<KeyValuePair>> {
        return collectionTypes
    }

    fun observeInstruments(): PublishSubject<Pair<Int, Pagination<Collection>>> {
        return instruments
    }

    fun getToken(): String? {
        return mainRepository.getToken()
    }
}