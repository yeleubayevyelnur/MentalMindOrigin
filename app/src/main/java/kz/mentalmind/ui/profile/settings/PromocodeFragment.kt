package kz.mentalmind.ui.profile.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    }

    companion object {

    }
}