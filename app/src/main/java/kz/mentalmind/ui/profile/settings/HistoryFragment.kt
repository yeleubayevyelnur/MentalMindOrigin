package kz.mentalmind.ui.profile.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_history.*
import kz.mentalmind.MainActivity
import kz.mentalmind.R
import kz.mentalmind.ui.profile.ProfileViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment : Fragment() {

    private val profileViewModel: ProfileViewModel by viewModel()
    private var compositeDisposable = CompositeDisposable()
    private var historyDate = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            historyDate = it.getString(HISTORY_DATE, "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileViewModel.getToken()?.let {
            observeData()
            profileViewModel.getHistory(it, historyDate)
        }
        btnBack.setOnClickListener {
            (activity as? MainActivity)?.onBackPressed()
        }
    }

    private fun observeData() {
        compositeDisposable.add(
            profileViewModel.observeErrorSubject().subscribe {
                (activity as? MainActivity)?.alertDialog(requireContext(), it)
            }
        )
        compositeDisposable.add(
            profileViewModel.observeHistorySubject().subscribe({
                rvHistory.adapter = HistoryAdapter(it.meditationData.results)
                if (it.error === null) {
                    (activity as? MainActivity)?.alertDialog(requireContext(), it.error)
                }
            }, {
                (activity as? MainActivity)?.alertDialog(requireContext(), it.message.toString())
            })
        )
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
        private const val HISTORY_DATE = "history_date"
        fun newInstance(date: String) = HistoryFragment().apply {
            arguments = Bundle().apply {
                putString(HISTORY_DATE, date)
            }
        }
    }
}