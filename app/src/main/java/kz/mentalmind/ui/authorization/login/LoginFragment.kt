package kz.mentalmind.ui.authorization.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_login.*
import kz.mentalmind.R
import kz.mentalmind.ui.authorization.AuthActivity
import kz.mentalmind.ui.authorization.AuthViewModel
import kz.mentalmind.ui.authorization.registration.RegistrationFragment
import kz.mentalmind.utils.Constants.GOOGLE
import kz.mentalmind.utils.Constants.GOOGLE_SIGN_IN
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginFragment : Fragment() {

    private val authViewModel: AuthViewModel by viewModel()
    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        compositeDisposable.add(
            authViewModel.observeLoginSubject().subscribe({
                if (it.error == null) {
                    authViewModel.saveUser(requireContext(), it.loginData.user)
                    authViewModel.saveToken(requireContext(), it.loginData.access_token)
                    (activity as? AuthActivity)?.openMainActivity()
                } else {
                    authViewModel.observeErrorSubject().subscribe {
                        (activity as? AuthActivity)?.alertDialog(requireContext(), it)
                    }
                }
            }, {

            })
        )

        tvRegistration.setOnClickListener {
            (activity as? AuthActivity)?.replaceFragment(
                RegistrationFragment(),
                RegistrationFragment::class.java.name
            )
        }
        btnLoginEmail.setOnClickListener {
            (activity as? AuthActivity)?.replaceFragment(
                LoginWithEmailFragment(),
                LoginWithEmailFragment::class.java.name
            )
        }

        btnLoginGoogle.setOnClickListener {
            loginWithGoogle()
        }
    }

    private fun loginWithGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("712578955819-3j2aj54vslg4mfvjoqu7jd5cc2ai43u9.apps.googleusercontent.com")
            .requestEmail()
            .build()
        val mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            account?.idToken?.let {
                authViewModel.socialLogin(GOOGLE, it)
            }
        } catch (e: ApiException) {

        }
    }
}