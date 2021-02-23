package kz.mentalmind.ui.purchase

import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import kz.mentalmind.data.dto.PaymentRequest
import kz.mentalmind.data.dto.PaymentResponse
import kz.mentalmind.data.dto.TariffsResponse
import kz.mentalmind.data.repository.MainRepository

class TariffsViewModel(private val mainRepository: MainRepository) : ViewModel() {

    private val disposable = CompositeDisposable()
    private val errorsSubject = PublishSubject.create<String>()
    private val tariffsSubject = PublishSubject.create<TariffsResponse>()
    private val paymentSubject = PublishSubject.create<PaymentResponse>()

    fun getTariffs(token: String) {
        disposable.add(
            mainRepository.getTariffs(token).observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.error == null) {
                        it.data?.let { tariffsSubject.onNext(it) }
                    } else {
                        errorsSubject.onNext(it.error)
                    }
                }, {
                    it.message?.let { errorsSubject.onNext(it) }
                })
        )
    }

    fun paymentInit(token: String, paymentRequest: PaymentRequest) {
        disposable.add(
            mainRepository.paymentInit(token, paymentRequest)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.error == null) {
                        it.data?.let { paymentSubject.onNext(it) }
                    } else {
                        errorsSubject.onNext(it.error)
                    }
                }, {
                    it.message?.let { errorsSubject.onNext(it) }
                })
        )
    }

    fun observeTariffsSubject(): Observable<TariffsResponse> {
        return tariffsSubject
    }

    fun observePaymentSubject(): Observable<PaymentResponse> {
        return paymentSubject
    }

    fun observeErrorSubject(): Observable<String> {
        return errorsSubject
    }

    fun getToken(): String? {
        return mainRepository.getToken()
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }
}