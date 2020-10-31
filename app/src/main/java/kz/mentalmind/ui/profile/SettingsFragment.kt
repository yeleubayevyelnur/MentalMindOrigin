package kz.mentalmind.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_settings.*
import kz.mentalmind.MainActivity
import kz.mentalmind.R
import kz.mentalmind.ui.profile.settings.ChangePasswordFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class SettingsFragment : Fragment() {

    private val compositeDisposable = CompositeDisposable()
    private val profileViewModel: ProfileViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnBack.setOnClickListener {
            (activity as? MainActivity)?.onBackPressed()
        }
        changePassword.setOnClickListener {
            (activity as? MainActivity)?.replaceFragment(
                ChangePasswordFragment(),
                ChangePasswordFragment::class.simpleName
            )
        }
    }

    override fun onStart() {
        super.onStart()
        (activity as? MainActivity)?.hideBottomNavigation()
    }

    override fun onStop() {
        super.onStop()
        (activity as? MainActivity)?.showBottomNavigation()
    }

    companion object {
    }
}