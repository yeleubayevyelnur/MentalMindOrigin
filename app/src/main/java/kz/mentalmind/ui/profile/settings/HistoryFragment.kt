package kz.mentalmind.ui.profile.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_history.*
import kz.mentalmind.MainActivity
import kz.mentalmind.R
import kz.mentalmind.data.MeditationResult
import kz.mentalmind.ui.profile.ProfileViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment : Fragment() {

    private val profileViewModel: ProfileViewModel by viewModel()
    private var compositeDisposable = CompositeDisposable()
    private var historyDate = ""
    private lateinit var historyAdapter: HistoryAdapter
    private var historyList: ArrayList<MeditationResult> = arrayListOf()

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
                historyList = it.meditationData.results
                historyAdapter = HistoryAdapter(historyList)
                rvHistory.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                rvHistory.adapter = historyAdapter
                if (it.error.isNullOrEmpty()) {
                    (activity as? MainActivity)?.alertDialog(requireContext(), it.error)
                }
            }, {
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