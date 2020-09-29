package kz.mentalmind.ui.authorization.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_login.*
import kz.mentalmind.R
import kz.mentalmind.ui.authorization.AuthActivity
import kz.mentalmind.ui.authorization.registration.RegistrationFragment

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvRegistration.setOnClickListener {
            (activity as? AuthActivity)?.replaceFragment(RegistrationFragment(), RegistrationFragment::class.java.name)
        }
        btnLoginEmail.setOnClickListener {
            (activity as? AuthActivity)?.replaceFragment(LoginWithEmailFragment(), LoginWithEmailFragment::class.java.name)
        }
    }

    companion object {

    }
}