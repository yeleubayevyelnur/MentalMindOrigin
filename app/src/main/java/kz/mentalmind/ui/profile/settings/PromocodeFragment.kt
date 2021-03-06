package kz.mentalmind.ui.profile.settings

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_promocode.*
import kz.mentalmind.MainActivity
import kz.mentalmind.R
import kz.mentalmind.ui.profile.ProfileViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PromocodeFragment : Fragment() {

    private var compositeDisposable = CompositeDisposable()
    private val profileViewModel: ProfileViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_promocode, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        compositeDisposable.add(
            profileViewModel.observeErrorSubject().subscribe {
                (activity as? MainActivity)?.alertDialog(requireContext(), it)
            }
        )
        compositeDisposable.add(
            profileViewModel.observePromocodeSubject().subscribe({
                (activity as? MainActivity)?.alertDialog(requireContext(), it.result)
            }, {
                (activity as? MainActivity)?.alertDialog(requireContext(), it.message)
            })
        )
        btnNext.setOnClickListener {
            profileViewModel.getToken()
                ?.let { profileViewModel.promocode(it, etPromocode.text.toString()) }
        }
        btnNext.addTextChangedListener(textWatcher)
        btnBack.setOnClickListener {
            (activity as? MainActivity)?.onBackPressed()
        }
    }

    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val promocodeText: String = etPromocode.text.toString().trim()
            btnNext.isEnabled = promocodeText.isNotEmpty()
        }

        override fun afterTextChanged(s: Editable) {}
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