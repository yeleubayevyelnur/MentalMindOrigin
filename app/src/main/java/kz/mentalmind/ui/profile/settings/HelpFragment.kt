package kz.mentalmind.ui.profile.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_faq.btnBack
import kotlinx.android.synthetic.main.fragment_help.*
import kz.mentalmind.MainActivity
import kz.mentalmind.R
import kz.mentalmind.ui.profile.ProfileViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HelpFragment : Fragment() {

    private var compositeDisposable = CompositeDisposable()
    private val profileViewModel: ProfileViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_help, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        compositeDisposable.add(
            profileViewModel.observeHelpSubject().subscribe({
                if (it.error == null) {
                    successAlert()
                }
            }, {

            })
        )
        btnBack.setOnClickListener {
            (activity as? MainActivity)?.onBackPressed()
        }
        btnSend.setOnClickListener {
            profileViewModel.getToken()?.let { it1 ->
                profileViewModel.help(
                    it1,
                    etHelp.text.toString()
                )
            }
        }
    }

    private fun successAlert() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Мы все проверим")
        builder.setMessage("Ответ ожидайте на почту, указанную при регистрации")
        builder.setPositiveButton("Ок") { dialog, wich ->
            dialog.cancel()
            (activity as? MainActivity)?.onBackPressed()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    companion object {

    }
}