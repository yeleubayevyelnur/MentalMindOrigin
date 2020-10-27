package kz.mentalmind.ui.profile

import android.content.Context
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import kz.mentalmind.data.HelpResponse
import kz.mentalmind.data.PromocodeResponse
import kz.mentalmind.data.profile.LevelDetailResponse
import kz.mentalmind.data.profile.LevelsResponse
import kz.mentalmind.data.entrance.User
import kz.mentalmind.data.profile.ProfileResponse
import kz.mentalmind.data.repository.MainRepository

class ProfileViewModel(private val mainRepository: MainRepository) : ViewModel() {

    private val disposable = CompositeDisposable()
    private val errorsSubject = PublishSubject.create<String>()
    private val promocodeSubject = PublishSubject.create<PromocodeResponse>()
    private val profileSubject = PublishSubject.create<ProfileResponse>()
    private val levelsSubject = PublishSubject.create<LevelsResponse>()
    private val levelsDetailSubject = PublishSubject.create<LevelDetailResponse>()
    private val helpSubject = PublishSubject.create<HelpResponse>()

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
                    errorsSubject.onNext(it.message.toString())
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

    fun help(token: String, text: String) {
        disposable.add(
            mainRepository.help(token, text).observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.error == null) {
                        helpSubject.onNext(it)
                    } else {
                        errorsSubject.onNext(it.error)
                    }
                }, {

                })
        )
    }

    fun promocode(token: String, promocode: String) {
        disposable.add(
            mainRepository.promocode(token, promocode).observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.error == null) {
                        promocodeSubject.onNext(it)
                    } else {
                        errorsSubject.onNext(it.error)
                    }
                }, {

                })
        )
    }

    fun observePromocodeSubject(): Observable<PromocodeResponse> {
        return promocodeSubject
    }

    fun getToken(context: Context): String? {
        return mainRepository.getToken(context)
    }

    fun observeProfileSubject(): Observable<ProfileResponse> {
        return profileSubject
    }

    fun observeHelpSubject(): Observable<HelpResponse> {
        return helpSubject
    }

    fun observeErrorSubject(): Observable<String> {
        return errorsSubject
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