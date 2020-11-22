package kz.mentalmind.ui.profile

import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import kz.mentalmind.data.HelpResponse
import kz.mentalmind.data.Meditations
import kz.mentalmind.data.PromocodeResponse
import kz.mentalmind.data.profile.LevelDetailResponse
import kz.mentalmind.data.profile.LevelsResponse
import kz.mentalmind.data.profile.PassResetResponse
import kz.mentalmind.data.profile.ProfileResponse
import kz.mentalmind.data.repository.MainRepository

class ProfileViewModel(private val mainRepository: MainRepository) : ViewModel() {

    private val disposable = CompositeDisposable()
    private val errorsSubject = PublishSubject.create<String>()
    private val resetPassSubject = PublishSubject.create<PassResetResponse>()
    private val promocodeSubject = PublishSubject.create<PromocodeResponse>()
    private val profileSubject = PublishSubject.create<ProfileResponse>()
    private val levelsSubject = PublishSubject.create<LevelsResponse>()
    private val levelsDetailSubject = PublishSubject.create<LevelDetailResponse>()
    private val historySubject = PublishSubject.create<Meditations>()
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
                    }
                }, {

                })
        )
    }

    fun getHistory(token: String, date: String) {
        disposable.add(
            mainRepository.getHistory(token, date).observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.meditationData.results.isNotEmpty()) historySubject.onNext(it)
                    else errorsSubject.onNext("История пуста")
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
                    }
                }, {

                })
        )
    }

    fun passReset(token: String, oldPass: String, newPass: String) {
        disposable.add(
            mainRepository.passReset(token, oldPass, newPass)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.error == null && it.helpData.success) resetPassSubject.onNext(it)
                    else it.error?.let { it1 -> errorsSubject.onNext(it1) }
                }, {

                })
        )
    }

    fun observePassResetSubject(): Observable<PassResetResponse> {
        return resetPassSubject
    }

    fun observePromocodeSubject(): Observable<PromocodeResponse> {
        return promocodeSubject
    }

    fun getToken(): String? {
        return mainRepository.getToken()
    }

    fun observeProfileSubject(): Observable<ProfileResponse> {
        return profileSubject
    }

    fun observeHelpSubject(): Observable<HelpResponse> {
        return helpSubject
    }

    fun observeHistorySubject(): Observable<Meditations> {
        return historySubject
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