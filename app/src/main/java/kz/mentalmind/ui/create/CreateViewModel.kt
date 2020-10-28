package kz.mentalmind.ui.create

import android.content.Context
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import kz.mentalmind.data.Collections
import kz.mentalmind.data.KeyValueData
import kz.mentalmind.data.repository.MainRepository

class CreateViewModel(private val mainRepository: MainRepository) : ViewModel() {
    private val collectionTypes = PublishSubject.create<KeyValueData>()
    private val instruments = PublishSubject.create<Pair<Int, Collections>>()
    private val errorsSubject = PublishSubject.create<String>()

    fun getCollectionTypes(context: Context) {
        getToken()?.let {
            mainRepository.getCollectionsTypes(it, "ru")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    collectionTypes.onNext(response)
                }, {})
        }
    }

    fun getCollectionsByTypes(context: Context, type: Int) {
        getToken()?.let {
            mainRepository.getCollectionsByTypes(it, "ru", type)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    if (response.error == null) {
                        instruments.onNext(Pair(type, response.data))
                    } else {
                        errorsSubject.onNext(response.error)
                    }
                }, {
                    errorsSubject.onNext(it.message ?: "")
                })
        }
    }

    fun observeCollectionTypesSubject(): PublishSubject<KeyValueData> {
        return collectionTypes
    }

    fun observeInstruments(): PublishSubject<Pair<Int, Collections>> {
        return instruments
    }

    fun getToken(): String? {
        return mainRepository.getToken()
    }
}