package kz.mentalmind.ui.main

import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import kz.mentalmind.data.dto.*
import kz.mentalmind.data.repository.MainRepository

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {
    private val disposable = CompositeDisposable()
    private val errorsSubject = PublishSubject.create<String>()
    private val streamOfLife = PublishSubject.create<CommonResponse<Pagination<CollectionDto>>>()
    private val favorites = PublishSubject.create<CommonResponse<Pagination<FavoriteMeditationDto>>>()
    private val instrumentsForFeeling = PublishSubject.create<CommonResponse<Pagination<CollectionDto>>>()
    private val challenges = PublishSubject.create<List<ChallengeDto>>()
    private val courses = PublishSubject.create<List<CourseDto>>()

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

    fun observeStreamOfLife(): PublishSubject<CommonResponse<Pagination<CollectionDto>>> {
        return streamOfLife
    }

    fun observeFavorites(): PublishSubject<CommonResponse<Pagination<FavoriteMeditationDto>>> {
        return favorites
    }

    fun observeInstrumentsForFeeling(): PublishSubject<CommonResponse<Pagination<CollectionDto>>> {
        return instrumentsForFeeling
    }

    fun observeCourses(): PublishSubject<List<CourseDto>> {
        return courses
    }

    fun observeErrorSubject(): Observable<String> {
        return errorsSubject
    }

    fun observeChallengesResponse(): Observable<List<ChallengeDto>> {
        return challenges
    }

    fun getToken(): String? {
        return mainRepository.getToken()
    }
}