package kz.mentalmind.ui.instruments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_instruments.*
import kz.mentalmind.MainActivity
import kz.mentalmind.R
import kz.mentalmind.data.dto.CollectionDto
import kz.mentalmind.data.dto.KeyValuePairDto
import kz.mentalmind.ui.main.InstrumentClickListener
import kz.mentalmind.ui.main.InstrumentsAdapter
import kz.mentalmind.ui.main.MainAdapter
import kz.mentalmind.ui.meditations.MeditationsFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class InstrumentsFragment : Fragment() {
    private val viewModel: InstrumentsViewModel by viewModel()
    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_instruments, container, false)
        val instrumentAdapters = ArrayList<Pair<Int, InstrumentsAdapter>>()
        val tags = ArrayList<KeyValuePairDto>()
        val adapter = MainAdapter(tags, instrumentAdapters)

        compositeDisposable.add(viewModel.observeTagsSubject().subscribe({
            tags.addAll(it.results.subList(1, it.results.size))
            for (i in 1 until it.results.size) {
                viewModel.getCollectionsByTags(requireContext(), it.results[i].id)
            }
        }, {}))

        compositeDisposable.add(viewModel.observeInstruments().subscribe({
            instrumentAdapters.add(
                Pair(
                    it.first,
                    InstrumentsAdapter(
                        it.second.results,
                        object : InstrumentClickListener {
                            override fun onInstrumentClicked(meditation: CollectionDto) {
                                (activity as MainActivity).replaceFragment(
                                    MeditationsFragment.newInstance(meditation.id),
                                    MeditationsFragment::class.simpleName
                                )
                            }
                        })
                )
            )

            if (tags.size == instrumentAdapters.size) {
                instruments.adapter = adapter
            }
        }, {}))

        viewModel.getTags(requireContext())
        return view
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).showBottomNavigation()
    }

    override fun onDestroyView() {
        compositeDisposable.clear()
        super.onDestroyView()
    }
}