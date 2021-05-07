package kz.mentalmind.ui.home

import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import kz.mentalmind.data.dto.*
import kz.mentalmind.data.dto.Collection
import kz.mentalmind.data.repository.MainRepository

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {
    private val disposable = CompositeDisposable()
    private val errorsSubject = PublishSubject.create<String>()
    private val streamOfLife = PublishSubject.create<CommonResponse<Pagination<Collection>>>()
    private val challengeDetails = PublishSubject.create<CommonResponse<ChallengeDetailsDto>>()
    private val favorites =
        PublishSubject.create<CommonResponse<Pagination<FavoriteMeditation>>>()
    private val instrumentsForFeeling =
        PublishSubject.create<CommonResponse<Pagination<Collection>>>()
    private val challenges = PublishSubject.create<List<Challenge>>()
    private val courses = PublishSubject.create<List<Course>>()
    private val onlineListeners = PublishSubject.create<CommonResponse<OnlineListenersResponse>>()

    init {
        getOnlineListeners()
    }

    fun saveFeeling(id: Int) {
        mainRepository.saveFeeling(id)
    }

    fun getStreamOfLife(token: String) {
        disposable.add(
            mainRepository.getCollections(token, 1, 1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.error == null) {
                        streamOfLife.onNext(it)
                    } else {
                        errorsSubject.onNext(it.error)
                    }
                }, {
                    errorsSubject.onNext(it.message ?: "")
                })
        )
    }

    fun getCollectionsByFeeling(token: String) {
        val feeling = mainRepository.getFeeling()
        if (feeling == 9999) {
            return
        }
        disposable.add(
            mainRepository.getCollectionsByFeeling(token, feeling)
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

    fun getFavorites(token: String) {
        disposable.add(
            mainRepository.getFavorites(token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.error == null) {
                        favorites.onNext(it)
                    } else {
                        errorsSubject.onNext(it.error)
                    }
                }, {
                    errorsSubject.onNext(it.message ?: "")
                })
        )
    }

    fun getCourses(token: String) {
        courses
        disposable.add(
            mainRepository.getCourses(token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.error == null) {
                        if (it.data?.results != null) {
                            courses.onNext(it.data.results)
                        }
                    } else {
                        errorsSubject.onNext(it.error)
                    }
                }, {
                    errorsSubject.onNext(it.message ?: "")
                })
        )
    }

    fun getChallenges(token: String) {
        disposable.add(
            mainRepository.getChallenges(token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.error == null) {
                        if (it.data?.results != null) {
                            challenges.onNext(it.data.results)
                        }
                    } else {
                        errorsSubject.onNext(it.error)
                    }
                }, {
                    errorsSubject.onNext(it.message ?: "")
                })
        )
    }

    fun getChallengeDetails(challengeId: Int) {
        val token = getToken()
        if (token != null) {
            disposable.add(
                mainRepository.getChallengeDetails(token, challengeId)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        if (it.error == null) {
                            challengeDetails.onNext(it)
                        } else {
                            errorsSubject.onNext(it.error)
                        }
                    }, {
                        errorsSubject.onNext(it.message ?: "")
                    })
            )
        } else {
            // TODO разлогинить
        }
    }

    fun getOnlineListeners() {
        val token = getToken()
        if (token != null) {
            disposable.add(
                mainRepository.getOnlineListeners(token)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        if (it.error == null) {
                            onlineListeners.onNext(it)
                        } else {
                            errorsSubject.onNext(it.error)
                        }
                    }, {

                    })
            )
        }
    }

    fun observeStreamOfLife(): PublishSubject<CommonResponse<Pagination<Collection>>> {
        return streamOfLife
    }

    fun observeFavorites(): PublishSubject<CommonResponse<Pagination<FavoriteMeditation>>> {
        return favorites
    }

    fun observeInstrumentsForFeeling(): PublishSubject<CommonResponse<Pagination<Collection>>> {
        return instrumentsForFeeling
    }

    fun observeCourses(): PublishSubject<List<Course>> {
        return courses
    }

    fun observeErrorSubject(): Observable<String> {
        return errorsSubject
    }

    fun observeChallengesResponse(): Observable<List<Challenge>> {
        return challenges
    }

    fun observeChallengeDetails(): Observable<CommonResponse<ChallengeDetailsDto>> {
        return challengeDetails
    }

    fun observeOnlineListeners() : Observable<CommonResponse<OnlineListenersResponse>> {
        return onlineListeners
    }

    fun getToken(): String? {
        return mainRepository.getToken()
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }
}