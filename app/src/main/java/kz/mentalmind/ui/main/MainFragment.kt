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

class MainFragment : Fragment() {
    private val viewModel: MainViewModel by viewModel()
    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        viewModel.getToken(requireContext())?.let {
            viewModel.getStreamOfLife(it)
            viewModel.getCollectionsByFeeling(it, 1)
        }
        moodView.setOnClickListener {
            (activity as? MainActivity)?.replaceFragment(
                MoodFragment(),
                MoodFragment::class.simpleName
            )
        }
    }

    private fun observeData() {
        compositeDisposable.add(
            viewModel.observeStreamOfLife().subscribe({
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
            viewModel.observeInstrumentsForFeeling().subscribe({
                rvRecommended.adapter = InstrumentsAdapter(
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
            viewModel.observeErrorSubject().subscribe {
                (activity as? MainActivity)?.alertDialog(requireContext(), it)
            }
        )
    }
}