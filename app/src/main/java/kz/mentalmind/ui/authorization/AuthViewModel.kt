package kz.mentalmind.ui.authorization

import android.util.Log
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import kz.mentalmind.data.entrance.*
import kz.mentalmind.data.repository.AuthRepository

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private val disposable = CompositeDisposable()
    private val errorsSubject = PublishSubject.create<String>()
    private val registerSubject = PublishSubject.create<RegisterData>()
    private val loginSubject = PublishSubject.create<LoginData>()
    private val passwordRecoverySubject = PublishSubject.create<PassRecoveryData>()
    private val progressVisible = PublishSubject.create<Boolean>()

    fun register(email: String, password: String, language: String) {
        disposable.add(
            authRepository.register(email, password, language)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.error == null) {
                        registerSubject.onNext(it)
                    } else {
                        errorsSubject.onNext(it.error)
                    }
                }, {
                    Log.e("error", it.message.toString())
                    it.message?.let { it1 -> errorsSubject.onNext(it1) }
                })
        )
    }

    fun login(email: String, password: String) {
        disposable.add(
            authRepository.login(email, password)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { progressVisible.onNext(true) }
                .doOnComplete {  progressVisible.onNext(false) }
                .subscribe({
                    Log.d("yel", "subscribe")
                    if (it.error == null) {
                        saveUser(it.data.user)
                        saveToken(it.data.access_token)
                        loginSubject.onNext(it.data)
                    } else {
                        errorsSubject.onNext(it.error)
                    }
                }, {
                    Log.e("error", it.message.toString())
                    it.message?.let { it1 -> errorsSubject.onNext(it1) }
                })
        )
    }

    fun socialLogin(type: String, token: String, email: String? = null) {
        disposable.add(
            authRepository.socialLogin(SocialInfo(type, token, email))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.error == null) {
                        saveUser(it.data.user)
                        saveToken(it.data.access_token)
                        loginSubject.onNext(it.data)
                    } else {
                        errorsSubject.onNext(it.error)
                    }
                }, {
                    Log.e("error", it.message.toString())
                    it.message?.let { it1 -> errorsSubject.onNext(it1) }
                })
        )
    }

    fun passwordRecovery(email: String) {
        disposable.add(
            authRepository.passwordRecovery(email)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    passwordRecoverySubject.onNext(it)
                }, {
                    Log.e("error", it.message.toString())
                    it.message?.let { it1 -> errorsSubject.onNext(it1) }
                })
        )
    }

    fun observePassRecoverySubject(): PublishSubject<PassRecoveryData> {
        return passwordRecoverySubject
    }

    fun observeRegisterSubject(): Observable<RegisterData> {
        return registerSubject
    }

    fun observeLoginSubject(): Observable<LoginData> {
        return loginSubject
    }

    fun observeProgressVisibility(): Observable<Boolean> {
        return progressVisible
    }

    private fun saveUser(user: User) {
        authRepository.saveUser(user)
    }

    private fun saveToken(accessToken: String) {
        authRepository.saveToken(accessToken)
    }

    fun getUser(): User? {
        return authRepository.getUser()
    }

    fun observeErrorSubject(): Observable<String> {
        return errorsSubject
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }
}