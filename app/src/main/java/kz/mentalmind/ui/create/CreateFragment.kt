package kz.mentalmind.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_instruments.*
import kz.mentalmind.MainActivity
import kz.mentalmind.R
import kz.mentalmind.data.CollectionItem
import kz.mentalmind.data.KeyValuePair
import kz.mentalmind.ui.main.InstrumentClickListener
import kz.mentalmind.ui.main.InstrumentsAdapter
import kz.mentalmind.ui.meditations.MeditationsFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateFragment : Fragment() {
    private val viewModel: CreateViewModel by viewModel()
    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val instrumentAdapters = ArrayList<Pair<Int, InstrumentsAdapter>>()
        val types = ArrayList<KeyValuePair>()
        val adapter = CreationAdapter(types, instrumentAdapters)

        compositeDisposable.add(viewModel.observeCollectionTypesSubject().subscribe {
            types.addAll(it.results.subList(1, it.results.size))
            for (i in 1 until it.results.size) {
                viewModel.getCollectionsByTypes(requireContext(), it.results[i].id)
            }
        })

        compositeDisposable.add(viewModel.observeInstruments().subscribe({
            instrumentAdapters.add(
                Pair(
                    it.first,
                    InstrumentsAdapter(
                        it.second.results,
                        object : InstrumentClickListener {
                            override fun onInstrumentClicked(meditation: CollectionItem) {
                                (activity as MainActivity).replaceFragment(
                                    MeditationsFragment.newInstance(meditation.id),
                                    MeditationsFragment::class.simpleName
                                )
                            }
                        })
                )
            )

            if (types.size == instrumentAdapters.size) {
                instruments.adapter = adapter
            }
        }, {}))

        viewModel.getCollectionTypes(requireContext())
    }
}