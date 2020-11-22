package kz.mentalmind.ui.player

import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kz.mentalmind.data.dto.AddToFavorites
import kz.mentalmind.data.repository.MainRepository

class PlayerViewModel(private val mainRepository: MainRepository) : ViewModel() {
    private val disposable = CompositeDisposable()

    fun addToFavorites(meditationId: Int, collectionId: Int) {
        getToken()?.let {
            disposable.add(
                mainRepository.addToFavorites(it, AddToFavorites(meditationId, collectionId))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                    }, {
                    })
            )
        }
    }

    fun getToken(): String? {
        return mainRepository.getToken()
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }

    fun setRating(meditationId: Int, star: Int) {
        getToken()?.let {
            disposable.add(
                mainRepository.setRating(it, meditationId, star)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                    }, {
                    })
            )
        }
    }
}