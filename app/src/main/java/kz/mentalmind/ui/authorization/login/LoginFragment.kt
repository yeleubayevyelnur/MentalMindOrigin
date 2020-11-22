package kz.mentalmind.ui.authorization.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.pusher.pushnotifications.PushNotifications
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_login.*
import kz.mentalmind.R
import kz.mentalmind.ui.authorization.AuthActivity
import kz.mentalmind.ui.authorization.AuthViewModel
import kz.mentalmind.ui.authorization.registration.RegistrationFragment
import kz.mentalmind.utils.Constants.FACEBOOK
import kz.mentalmind.utils.Constants.GOOGLE
import kz.mentalmind.utils.Constants.GOOGLE_SIGN_IN
import kz.mentalmind.utils.Constants.VKONTAKTE
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginFragment : Fragment() {
    private lateinit var callbackManager: CallbackManager
    private lateinit var btnLoginFacebook: LoginButton
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
        initFacebook(view)
        compositeDisposable.add(
            authViewModel.observeLoginSubject().subscribe({
                PushNotifications.addDeviceInterest(it.user.email)
                (activity as? AuthActivity)?.openMainActivity()
            }, {})
        )

        compositeDisposable.add(authViewModel.observeErrorSubject().subscribe {
            (activity as? AuthActivity)?.alertDialog(requireContext(), it)
        })

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

        btnLoginVk.setOnClickListener {
            VK.login(requireActivity(), arrayListOf(VKScope.EMAIL, VKScope.OFFLINE))
        }
    }

    private fun initFacebook(view: View) {
        callbackManager = CallbackManager.Factory.create()
        btnLoginFacebook = view.findViewById<LoginButton>(R.id.btnLoginFacebook)
        btnLoginFacebook.setPermissions("email")
        btnLoginFacebook.fragment = this
        btnLoginFacebook.registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
            override fun onSuccess(loginResult: LoginResult?) {
                handleFacebookSignInResult(loginResult?.accessToken)
            }

            override fun onCancel() {
            }

            override fun onError(exception: FacebookException) {
            }
        })
    }

    private fun loginWithGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.google_token))
            .requestEmail()
            .build()
        val mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            GOOGLE_SIGN_IN -> {
                val task: Task<GoogleSignInAccount> =
                    GoogleSignIn.getSignedInAccountFromIntent(data)
                handleGoogleSignInResult(task)
                super.onActivityResult(requestCode, resultCode, data)
            }
            btnLoginFacebook.requestCode -> {
                callbackManager.onActivityResult(requestCode, resultCode, data)
                super.onActivityResult(requestCode, resultCode, data)
            }
            else -> {
                val callback = object : VKAuthCallback {
                    override fun onLogin(token: VKAccessToken) {
                        handleVkSignInResult(token)
                    }

                    override fun onLoginFailed(errorCode: Int) {
                        // User didn't pass authorization
                    }
                }
                if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
                    super.onActivityResult(requestCode, resultCode, data)
                }
            }
        }
    }

    private fun handleGoogleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            account?.idToken?.let {
                authViewModel.socialLogin(GOOGLE, it)
            }
        } catch (e: ApiException) {
        }
    }

    private fun handleFacebookSignInResult(accessToken: AccessToken?) {
        try {
            accessToken?.token?.let {
                authViewModel.socialLogin(FACEBOOK, it)
            }
        } catch (e: ApiException) {
        }
    }

    fun handleVkSignInResult(vkAccessToken: VKAccessToken) {
        try {
            authViewModel.socialLogin(
                VKONTAKTE,
                vkAccessToken.accessToken,
                vkAccessToken.email ?: ""
            )
        } catch (e: ApiException) {

        }
    }
}