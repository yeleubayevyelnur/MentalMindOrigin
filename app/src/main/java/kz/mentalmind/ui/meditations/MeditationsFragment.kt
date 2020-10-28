package kz.mentalmind.ui.meditations

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_player.back
import kotlinx.android.synthetic.main.fragment_meditations.*
import kz.mentalmind.MainActivity
import kz.mentalmind.R
import kz.mentalmind.domain.dto.CollectionDetailsDto
import kz.mentalmind.ui.player.PlayerActivity
import kz.mentalmind.utils.Constants
import kz.mentalmind.utils.Constants.ID
import org.koin.androidx.viewmodel.ext.android.viewModel

class MeditationsFragment : Fragment() {
    private val meditationsViewModel: MeditationsViewModel by viewModel()
    private val compositeDisposable = CompositeDisposable()
    private var collectionDetails: CollectionDetailsDto? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as? MainActivity)?.hideBottomNavigation()
    }

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
            activity?.onBackPressed()
        }

        play.setOnClickListener {
            collectionDetails?.let { collectionDetails ->
                startActivity(Intent(requireActivity(), PlayerActivity::class.java).apply {
                    putExtra(Constants.MEDITATION, collectionDetails.meditations.first())
                })
            }
        }
    }

    companion object {
        fun newInstance(id: Int) = MeditationsFragment().apply {
            arguments = Bundle().apply { putInt(ID, id) }
        }
    }
}