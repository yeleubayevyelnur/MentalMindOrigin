package kz.mentalmind.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_main.*
import kz.mentalmind.MainActivity
import kz.mentalmind.R
import kz.mentalmind.data.CollectionItem
import kz.mentalmind.ui.main.mood.MoodFragment
import kz.mentalmind.ui.meditations.MeditationsFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment(), InstrumentClickListener {
    private val mainViewModel: MainViewModel by viewModel()
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as? MainActivity)?.hideActionBar()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        mainViewModel.getToken(requireContext())?.let { mainViewModel.getStreamOfLife(it, "ru") }
        moodView.setOnClickListener {
            (activity as? MainActivity)?.replaceFragment(
                MoodFragment(),
                MoodFragment::class.simpleName
            )
        }
    }

    private fun observeData() {
        compositeDisposable.add(
            mainViewModel.observeStreamOfLife().subscribe({
                rvStreamOfLife.adapter = InstrumentsAdapter(
                    it.data.results,
                    object : InstrumentClickListener {
                        override fun onInstrumentClicked(meditation: CollectionItem) {
                            (activity as MainActivity).replaceFragment(
                                MeditationsFragment.newInstance(meditation.id),
                                MeditationsFragment::class.simpleName
                            )
                        }
                    }
                )
            }, {

            })
        )
        compositeDisposable.add(
            mainViewModel.observeErrorSubject().subscribe {
                (activity as? MainActivity)?.alertDialog(requireContext(), it)
            }
        )
    }

    override fun onInstrumentClicked(collections: CollectionItem) {
        (activity as? MainActivity)?.replaceFragment(
            MainItemFragment.newInstance(),
            MainItemFragment::class.simpleName
        )
    }
}