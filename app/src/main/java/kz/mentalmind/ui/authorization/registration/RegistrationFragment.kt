package kz.mentalmind.ui.authorization.registration

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_registration.*
import kotlinx.android.synthetic.main.fragment_registration.btnBack
import kotlinx.android.synthetic.main.fragment_registration.btnNext
import kz.mentalmind.R
import kz.mentalmind.ui.authorization.AuthActivity
import kz.mentalmind.ui.authorization.AuthViewModel
import kz.mentalmind.ui.authorization.login.LoginWithEmailFragment
import kz.mentalmind.utils.isValidEmail
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegistrationFragment : Fragment() {

    private val authViewModel: AuthViewModel by viewModel()
    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        compositeDisposable.add(
            authViewModel.observeRegisterSubject().subscribe {
            }
        )
        email.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                btnNext.isEnabled = s.isValidEmail()
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
        btnNext.setOnClickListener {
            registrationErrors()
        }
        btnBack.setOnClickListener {
            (activity as? AuthActivity)?.onBackPressed()
        }
    }

    private fun registrationErrors() {
        when {
            email.text.isNullOrEmpty() || password.text.isNullOrEmpty() || repPassword.text.isNullOrEmpty() -> {
                (activity as? AuthActivity)?.alertDialog(requireContext(), "Заполните поля")
            }
            password.text.toString() != repPassword.text.toString() -> {
                (activity as? AuthActivity)?.alertDialog(requireContext(), "Пароли не совпадают")
            }
            password.text?.length ?: 0 < 8 -> {
                (activity as? AuthActivity)?.alertDialog(
                    requireContext(),
                    "Длина пароля должна быть не менее 8 символов"
                )
            }
            else -> {
                if (isValidPassword(password.text.toString())) {
                    successPassCheck()
                } else {
                    (activity as? AuthActivity)?.alertDialog(
                        requireContext(),
                        "Пароль должен содержать как минимум одну заглавную букву и одну цифру"
                    )
                }
            }
        }
    }

    private fun successPassCheck() {
        authViewModel.register(email.text.toString(), repPassword.text.toString(), "kk-KZ")
        successDialog()
    }

    private fun successDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Добро пожаловать!")
            .setMessage("Письмо с сылкой для подтверждения электронного адреса отправлено на Ваш почтовый ящик, необходимо подтвердить в течении 24 часов")
            .setPositiveButton("Хорошо") { dialog, wich ->
                (activity as? AuthActivity)?.replaceFragment(
                    LoginWithEmailFragment(),
                    LoginWithEmailFragment::class.java.simpleName
                )
                dialog.cancel()
            }
            .create().show()
    }

    private fun isValidPassword(password: String): Boolean {
        val passwordPattern = "^(?=.*[A-Z])(?=.*[0-9])"
        val passwordMatcher = Regex(passwordPattern)
        return passwordMatcher.find(password) != null
    }
}