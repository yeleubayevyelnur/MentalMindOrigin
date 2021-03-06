package kz.mentalmind.ui.authorization.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pusher.pushnotifications.PushNotifications
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_login_with_email.*
import kz.mentalmind.R
import kz.mentalmind.ui.authorization.AuthActivity
import kz.mentalmind.ui.authorization.AuthViewModel
import kz.mentalmind.ui.authorization.registration.RegistrationFragment
import kz.mentalmind.ui.authorization.resetPass.PassResetFragment
import kz.mentalmind.utils.Constants
import kz.mentalmind.utils.isValidEmail
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginWithEmailFragment : Fragment() {

    private val authViewModel: AuthViewModel by viewModel()
    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login_with_email, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        compositeDisposable.add(
            authViewModel.observeLoginSubject().subscribe({
                PushNotifications.addDeviceInterest(it.user.email)
                (activity as? AuthActivity)?.openMainActivity()
                progress.visibility = View.GONE
            }, {
            })
        )
        compositeDisposable.add(authViewModel.observeErrorSubject().subscribe { error ->
            val errorMessage = when (error) {
                Constants.error_400 -> getString(R.string.http_400_message)
                Constants.error_403 -> getString(R.string.http_403_message)
                else -> error
            }
            (activity as? AuthActivity)?.alertDialog(requireContext(), errorMessage)
            progress.visibility = View.GONE
        })
        tvRegistration.setOnClickListener {
            (activity as? AuthActivity)?.replaceFragment(
                RegistrationFragment(),
                RegistrationFragment::class.java.name
            )
        }
        tvForgotPassword.setOnClickListener {
            (activity as? AuthActivity)?.replaceFragment(
                PassResetFragment(),
                PassResetFragment::class.java.name
            )
        }
        enterLogin.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                btnNext.isEnabled = s.isValidEmail()
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
        btnNext.setOnClickListener {
            login()
        }
        btnBack.setOnClickListener {
            (activity as? AuthActivity)?.onBackPressed()
        }
    }

    private fun login() {
        progress.visibility = View.VISIBLE
        if (enterLogin.text.isNullOrEmpty() || enterPassword.text.isNullOrEmpty()) {
            (activity as? AuthActivity)?.alertDialog(requireContext(), "Заполните все поля")
            progress.visibility = View.GONE
        } else {
            authViewModel.login(enterLogin.text.toString(), enterPassword.text.toString())
        }
    }

    private val loginTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val usernameInput: String = enterLogin.text.toString().trim()
            val passwordInput: String = enterPassword.text.toString().trim()
            btnNext.isEnabled = usernameInput.isNotEmpty() && passwordInput.isNotEmpty()
        }

        override fun afterTextChanged(s: Editable) {}
    }
}