package kz.mentalmind.ui.main

import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import kz.mentalmind.data.dto.CollectionDto
import kz.mentalmind.data.dto.CommonResponse
import kz.mentalmind.data.dto.Pagination
import kz.mentalmind.data.dto.ChallengeDto
import kz.mentalmind.data.dto.CourseDto
import kz.mentalmind.data.repository.MainRepository

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {
    private val disposable = CompositeDisposable()
    private val errorsSubject = PublishSubject.create<String>()
    private val streamOfLifeSubject =
        PublishSubject.create<CommonResponse<Pagination<CollectionDto>>>()
    private val instrumentsForFeeling =
        PublishSubject.create<CommonResponse<Pagination<CollectionDto>>>()
    private val challengesResponse = PublishSubject.create<List<ChallengeDto>>()
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
                        streamOfLifeSubject.onNext(it)
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

    fun observeStreamOfLife(): PublishSubject<CommonResponse<Pagination<CollectionDto>>> {
        return streamOfLifeSubject
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
        return challengesResponse
    }

    fun getToken(): String? {
        return mainRepository.getToken()
    }

    fun getChallenges(token: String) {
        disposable.add(
            mainRepository.getChallenges(token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.error == null) {
                        if (it.data?.results != null) {
                            challengesResponse.onNext(it.data.results)
                        }
                    } else {
                        errorsSubject.onNext(it.error)
                    }
                }, {
                    errorsSubject.onNext(it.message ?: "")
                })
        )
    }
}