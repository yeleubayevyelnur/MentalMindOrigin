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
                })
        )
    }

    fun login(email: String, password: String) {
        disposable.add(
            authRepository.login(email, password)
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