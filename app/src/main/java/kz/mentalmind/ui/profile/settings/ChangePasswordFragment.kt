package kz.mentalmind.ui.profile.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_change_password.*
import kz.mentalmind.MainActivity
import kz.mentalmind.R
import kz.mentalmind.ui.profile.ProfileFragment
import kz.mentalmind.ui.profile.ProfileViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChangePasswordFragment : Fragment() {

    private var compositeDisposable = CompositeDisposable()
    private val profileViewModel: ProfileViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_change_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        btnSave.setOnClickListener {
            if (enterPassword.text.toString().isEmpty() || etNewPass.text.toString().isEmpty() || etRepPass.text.toString().isEmpty()){
                showToast("Заполните все поля")
            }
            else {
                profileViewModel.getToken().let { token ->
                    token?.let { it1 ->
                        profileViewModel.passReset(
                            it1,
                            etNewPass.text.toString(),
                            etRepPass.text.toString()
                        )
                    }
                }
            }

        }
        btnBack.setOnClickListener {
            (activity as? MainActivity)?.onBackPressed()
        }
    }

    private fun observeData() {
        compositeDisposable.add(
            profileViewModel.observeErrorSubject().subscribe {
                showToast(it)
            })
        compositeDisposable.add(
            profileViewModel.observePassResetSubject().subscribe({ passResetResponse ->
                if (passResetResponse.helpData.success) {
                    (activity as? MainActivity)?.let {
                        it.replaceFragment(ProfileFragment())
                        it.alertDialog(requireContext(), "Пароль успешно изменен")
                    }
                } else {
                    profileViewModel.observeErrorSubject().subscribe {
                        showToast(it)
                    }
                }
            }, {

            })
        )

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as? MainActivity)?.hideBottomNavigation()
    }

    override fun onDetach() {
        super.onDetach()
        (activity as? MainActivity)?.showBottomNavigation()
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}