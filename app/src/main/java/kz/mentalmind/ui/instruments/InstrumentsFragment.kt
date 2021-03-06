package kz.mentalmind.ui.instruments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Router
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_instruments.*
import kz.mentalmind.MainActivity
import kz.mentalmind.R
import kz.mentalmind.data.dto.Collection
import kz.mentalmind.data.dto.KeyValuePair
import kz.mentalmind.navigation.Screens
import kz.mentalmind.ui.home.instruments.InstrumentClickListener
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class InstrumentsFragment : Fragment() {
    private val viewModel: InstrumentsViewModel by viewModel()
    private val compositeDisposable = CompositeDisposable()
    private val router: Router by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_instruments, container, false)
        val instrumentAdapters = ArrayList<Pair<Int, InstrumentsAdapter1>>()
        val tags = ArrayList<KeyValuePair>()
        val adapter = MainAdapter1(tags, instrumentAdapters)
        compositeDisposable.add(
            viewModel.observeProgressVisibility().subscribe({
                val visibility = if (it == true) View.VISIBLE
                else View.GONE
                progress.visibility = visibility
            }, {})
        )
        compositeDisposable.add(viewModel.observeTagsSubject().subscribe({
            tags.addAll(it.results.subList(1, it.results.size))
            for (i in 1 until it.results.size) {
                viewModel.getCollectionsByTags(requireContext(), it.results[i].id)
            }
        }, {}))

        compositeDisposable.add(viewModel.observeInstruments().subscribe({
            (activity as MainActivity).progressVisible(false)
            instrumentAdapters.add(
                Pair(
                    it.first,
                    InstrumentsAdapter1(
                        it.second.results,
                        object : InstrumentClickListener {
                            override fun onInstrumentClicked(meditation: Collection) {
                                router.navigateTo(Screens.meditationsFragment(meditation.id), false)
//                                (activity as MainActivity).replaceFragment(
//                                    MeditationsFragment.newInstance(meditation.id),
//                                    MeditationsFragment::class.simpleName
//                                )
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

    override fun onDestroyView() {
        compositeDisposable.clear()
        super.onDestroyView()
    }
}