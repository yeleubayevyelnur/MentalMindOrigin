package kz.mentalmind.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_create.*
import kotlinx.android.synthetic.main.fragment_instruments.instruments
import kz.mentalmind.MainActivity
import kz.mentalmind.R
import kz.mentalmind.data.dto.Affirmation
import kz.mentalmind.data.dto.Collection
import kz.mentalmind.data.dto.KeyValuePair
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
        val types = ArrayList<KeyValuePair>()
        val instrumentAdapters = ArrayList<Pair<Int, InstrumentsAdapter>>()
        val adapter = CreationAdapter(types, instrumentAdapters)

        compositeDisposable.add(viewModel.observeCollectionTypesSubject().subscribe({
            types.addAll(it.results.subList(1, it.results.size))
            for (i in 1 until it.results.size) {
                viewModel.getCollectionsByTypes(it.results[i].id)
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
                            override fun onInstrumentClicked(meditation: Collection) {
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

        compositeDisposable.add(viewModel.observeAffirmations().subscribe({
            rvAffirmations.adapter =
                AffirmationsAdapter(it.results, object : AffirmationClickListener {
                    override fun onAffirmationClicked(affirmation: Affirmation) {
                        (activity as MainActivity).replaceFragment(
                            AffirmationFragment.newInstance(affirmation),
                            AffirmationFragment::class.simpleName
                        )
                    }
                })
        }, {}))

        viewModel.getCollectionTypes()
        viewModel.getAffirmations()
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