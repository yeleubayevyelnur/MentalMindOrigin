package kz.mentalmind.ui.authorization

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import kz.mentalmind.data.entrance.LoginResponse
import kz.mentalmind.data.entrance.PassRecoveryData
import kz.mentalmind.data.entrance.RegisterData
import kz.mentalmind.data.entrance.User
import kz.mentalmind.data.repository.AuthRepository

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private val disposable = CompositeDisposable()
    private val errorsSubject = PublishSubject.create<String>()
    private val registerSubject = PublishSubject.create<RegisterData>()
    private val loginSubject = PublishSubject.create<LoginResponse>()
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
                        loginSubject.onNext(it)
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

    fun observeLoginSubject(): Observable<LoginResponse> {
        return loginSubject
    }

    fun saveUser(context: Context, user: User) {
        authRepository.saveUser(user, context)
    }

    fun saveToken(context: Context, accessToken: String) {
        authRepository.saveToken(context, accessToken)
    }

    fun getToken(context: Context): String? {
        return authRepository.getToken(context)
    }

    fun getUser(context: Context): User? {
        return authRepository.getUser(context)
    }

    fun observeErrorSubject(): Observable<String> {
        return errorsSubject
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }
}