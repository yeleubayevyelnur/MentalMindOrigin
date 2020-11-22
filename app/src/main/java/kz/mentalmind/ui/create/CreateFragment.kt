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
import kz.mentalmind.data.dto.CollectionDto
import kz.mentalmind.data.dto.KeyValuePairDto
import kz.mentalmind.ui.main.instruments.InstrumentClickListener
import kz.mentalmind.ui.main.instruments.InstrumentsAdapter
import kz.mentalmind.ui.meditations.MeditationsFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateFragment : Fragment() {
    private val viewModel: CreateViewModel by viewModel()
    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create, container, false)
        val types = ArrayList<KeyValuePairDto>()
        val instrumentAdapters = ArrayList<Pair<Int, InstrumentsAdapter>>()
        val adapter = CreationAdapter(types, instrumentAdapters)

        compositeDisposable.add(viewModel.observeCollectionTypesSubject().subscribe({
            types.addAll(it.results.subList(1, it.results.size))
            for (i in 1 until it.results.size) {
                viewModel.getCollectionsByTypes(requireContext(), it.results[i].id)
            }
        }, {

        }))

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

            if (types.size == instrumentAdapters.size) {
                instruments.adapter = adapter
            }
        }, {}))

        viewModel.getCollectionTypes(requireContext())
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