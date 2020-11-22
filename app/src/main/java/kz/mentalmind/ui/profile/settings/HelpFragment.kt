package kz.mentalmind.ui.profile.settings

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
                } else {
                    profileViewModel.observeErrorSubject().subscribe {
                    }
                }
            }, {

            })
        )
        btnBack.setOnClickListener {
            (activity as? MainActivity)?.onBackPressed()
        }
        etHelp.addTextChangedListener(textWatcher)
        btnSend.setOnClickListener {
            profileViewModel.getToken()?.let { it1 ->
                profileViewModel.help(
                    it1,
                    etHelp.text.toString()
                )
            }
        }
        btnBack.setOnClickListener {
            (activity as? MainActivity)?.onBackPressed()
        }
    }

    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val helpText: String = etHelp.text.toString().trim()
            btnSend.isEnabled = helpText.isNotEmpty()
        }

        override fun afterTextChanged(s: Editable) {}
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as? MainActivity)?.hideBottomNavigation()
    }

    override fun onDetach() {
        super.onDetach()
        (activity as? MainActivity)?.showBottomNavigation()
    }

    companion object {

    }
}