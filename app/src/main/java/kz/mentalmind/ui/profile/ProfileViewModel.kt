package kz.mentalmind.ui.profile

import android.content.Context
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import kz.mentalmind.data.profile.LevelDetailResponse
import kz.mentalmind.data.profile.LevelsResponse
import kz.mentalmind.data.entrance.User
import kz.mentalmind.data.profile.ProfileResponse
import kz.mentalmind.data.repository.MainRepository

class ProfileViewModel(private val mainRepository: MainRepository) : ViewModel() {

    private val disposable = CompositeDisposable()
    private val errorsSubject = PublishSubject.create<String>()
    private val profileSubject = PublishSubject.create<ProfileResponse>()
    private val levelsSubject = PublishSubject.create<LevelsResponse>()
    private val levelsDetailSubject = PublishSubject.create<LevelDetailResponse>()

    fun getProfile(token: String) {
        disposable.add(
            mainRepository.getProfile(token).observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.error == null) {
                        profileSubject.onNext(it)
                    } else {
                        errorsSubject.onNext(it.error)
                    }
                }, {

                })
        )
    }

    fun getLevels(token: String) {
        disposable.add(
            mainRepository.getLevels(token).observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.error == null) {
                        levelsSubject.onNext(it)
                    } else {
                        errorsSubject.onNext(it.error)
                    }
                }, {

                })
        )
    }

    fun getLevelDetail(token: String, id: Int) {
        disposable.add(
            mainRepository.getLevelDetail(token, id).observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.error == null) {
                        levelsDetailSubject.onNext(it)
                    } else {
                        errorsSubject.onNext(it.error)
                    }
                }, {

                })
        )
    }

    fun getUser(context: Context): User? {
        return mainRepository.getUser(context)
    }

    fun getToken(context: Context): String? {
        return mainRepository.getToken(context)
    }

    fun observeProfileSubject(): Observable<ProfileResponse> {
        return profileSubject
    }

    fun observeLevelsSubject(): Observable<LevelsResponse> {
        return levelsSubject
    }

    fun observeLevelDetailSubject(): Observable<LevelDetailResponse> {
        return levelsDetailSubject
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }
}