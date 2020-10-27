package kz.mentalmind.ui.authorization.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_login_with_email.*
import kz.mentalmind.R
import kz.mentalmind.ui.authorization.AuthActivity
import kz.mentalmind.ui.authorization.AuthViewModel
import kz.mentalmind.ui.authorization.registration.RegistrationFragment
import kz.mentalmind.ui.authorization.resetPass.PassResetFragment
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
                (activity as? AuthActivity)?.openMainActivity()
                authViewModel.saveUser(requireContext(), it.loginData.user)
                authViewModel.saveToken(requireContext(), it.loginData.access_token)
            }, {

            })
        )

        enterLogin.addTextChangedListener(loginTextWatcher)
        enterPassword.addTextChangedListener(loginTextWatcher)
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
        btnNext.setOnClickListener {

            authViewModel.login("sultan_0029@mail.ru", "testUser1")
//            if (enterLogin.text.isEmpty() || enterPassword.text.isEmpty()){
//                (activity as? AuthActivity)?.alertDialog(requireContext(), "Заполните все поля")
//            } else {
//                successLogin()
//            }
        }
    }

    private fun successLogin() {
        authViewModel.login(enterLogin.text.toString(), enterPassword.text.toString())
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

    companion object {
    }
}