package kz.mentalmind.ui.authorization.resetPass

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_pass_reset.*
import kz.mentalmind.R
import kz.mentalmind.ui.authorization.AuthActivity
import kz.mentalmind.ui.authorization.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PassResetFragment : Fragment() {

    private val authViewModel: AuthViewModel by viewModel()
    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pass_reset, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? AuthActivity)?.supportActionBar?.title = "Восстановление пароля"
        compositeDisposable.add(
            authViewModel.observePassRecoverySubject().subscribe {
                if (it.success){
                    (activity as? AuthActivity)?.successDialog(requireContext(), "Отлично", "Письмо успешно отправлено, проверьте Ваш почтовый ящик")
                }
            }
        )
        btnNext.setOnClickListener {
            authViewModel.passwordRecovery(email.text.toString())
        }
    }

    companion object {

    }
}