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
import kz.mentalmind.data.CollectionResult
import kz.mentalmind.ui.main.mood.MoodFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment(), ItemResultListener {

    private val mainViewModel: MainViewModel by viewModel()
    private val compositeDisposable = CompositeDisposable()

    private lateinit var collectionAdapter: CollectionAdapter

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
        mainViewModel.getCollections("ru")
        mainViewModel.getTags("ru")
        moodView.setOnClickListener {
            (activity as? MainActivity)?.replaceFragment(
                MoodFragment(),
                MoodFragment::class.simpleName
            )
        }
    }

    private fun observeData() {
        compositeDisposable.add(
            mainViewModel.observeCollectionsSubject().subscribe({
                collectionAdapter.setNewData(it.collections.results)
            }, {

            })
        )
        compositeDisposable.add(
            mainViewModel.observeTagsSubject().subscribe({

            }, {

            })
        )
        compositeDisposable.add(
            mainViewModel.observeErrorSubject().subscribe {
                (activity as? MainActivity)?.alertDialog(requireContext(), it)
            }
        )
    }

    override fun onItemClickedResult(collections: CollectionResult) {
        (activity as? MainActivity)?.replaceFragment(
            MainItemFragment.newInstance(),
            MainItemFragment::class.simpleName
        )
    }
}