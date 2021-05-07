package kz.mentalmind.ui.meditations

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Router
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_player.back
import kotlinx.android.synthetic.main.fragment_meditations.*
import kz.mentalmind.MainActivity
import kz.mentalmind.R
import kz.mentalmind.data.dto.CollectionDetails
import kz.mentalmind.ui.player.PlayerActivity
import kz.mentalmind.utils.Constants
import kz.mentalmind.utils.Constants.ID
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MeditationsFragment : Fragment() {
    private val meditationsViewModel: MeditationsViewModel by viewModel()
    private val compositeDisposable = CompositeDisposable()
    private var collectionDetails: CollectionDetails? = null
    private val router: Router by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_meditations, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val playListFragment =
            childFragmentManager.findFragmentByTag("playList") as? PlayListFragment
        compositeDisposable.add(meditationsViewModel.observeCollectionDetails().subscribe({
            collectionDetails = it
            playListFragment?.setData(it)
        }, {}))
        meditationsViewModel.getToken()
            ?.let { meditationsViewModel.getCollectionDetails(it, requireArguments().getInt(ID)) }

        back.setOnClickListener {
//            router.replaceScreen()
            activity?.onBackPressed()
        }

        play.setOnClickListener {
            collectionDetails?.let { collectionDetails ->
                startActivity(Intent(requireActivity(), PlayerActivity::class.java).apply {
                    putExtra(Constants.MEDITATION, collectionDetails.meditations.first())
                    putExtra(Constants.COLLECTION_ID, collectionDetails.id)
                })
            }
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as? MainActivity)?.hideBottomNavigation()
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as? MainActivity)?.showBottomNavigation()
    }

    companion object {
        fun newInstance(id: Int) = MeditationsFragment().apply {
            arguments = Bundle().apply { putInt(ID, id) }
        }
    }
}