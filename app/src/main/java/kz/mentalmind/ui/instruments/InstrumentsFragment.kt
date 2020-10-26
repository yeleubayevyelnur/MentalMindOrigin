package kz.mentalmind.ui.instruments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_instruments.*
import kz.mentalmind.R
import kz.mentalmind.data.CollectionItem
import kz.mentalmind.data.KeyValuePair
import kz.mentalmind.ui.main.InstrumentClickListener
import kz.mentalmind.ui.main.InstrumentsAdapter
import kz.mentalmind.ui.main.MainAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class InstrumentsFragment : Fragment() {
    private val viewModel: InstrumentsViewModel by viewModel()
    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_instruments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val instrumentAdapters = ArrayList<InstrumentsAdapter>()
        val tags = ArrayList<KeyValuePair>()
        val adapter = MainAdapter(tags, instrumentAdapters)

        compositeDisposable.add(viewModel.observeTagsSubject().subscribe {
            tags.addAll(it.results.subList(1, it.results.size))
            for (i in 1 until it.results.size) {
                viewModel.getCollectionsByTags(requireContext(), it.results[i].id)
            }
        })

        compositeDisposable.add(viewModel.observeInstruments().subscribe {
            instrumentAdapters.add(
                InstrumentsAdapter(
                    it.results,
                    object : InstrumentClickListener {
                        override fun onInstrumentClicked(meditation: CollectionItem) {

                        }
                    })
            )
            if (tags.size == instrumentAdapters.size) {
                instruments.adapter = adapter
            }
        })

        viewModel.getTags(requireContext())
    }
}