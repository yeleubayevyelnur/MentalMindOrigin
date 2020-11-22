package kz.mentalmind.ui.meditations

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_play_list.*
import kz.mentalmind.MainActivity
import kz.mentalmind.R
import kz.mentalmind.data.dto.CollectionDetailsDto
import kz.mentalmind.data.dto.MeditationDto
import kz.mentalmind.ui.player.PlayerActivity
import kz.mentalmind.ui.purchase.TrialFragment
import kz.mentalmind.utils.Constants
import kz.mentalmind.utils.Constants.MEDITATION


class PlayListFragment : BottomSheetDialogFragment() {
    private var viewBehavior: BottomSheetBehavior<View>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_play_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewState(parentFragment)
    }

    fun setData(collection: CollectionDetailsDto) {
        title.text = collection.name
        description.text = collection.description
        val adapter = MeditationsAdapter(collection.meditations, object : MeditationClickListener {
            override fun onMeditationClicked(meditation: MeditationDto) {
                if (isMeditationAvailable(meditation)) {
                    startActivity(Intent(requireActivity(), PlayerActivity::class.java).apply {
                        putExtra(MEDITATION, meditation)
                        putExtra(Constants.COLLECTION_ID, collection.id)
                    })
                } else {
                    (activity as MainActivity).replaceFragment(
                        TrialFragment.newInstance(),
                        TrialFragment::class.simpleName
                    )
                }
            }
        })
        meditations.adapter = adapter
    }

    private fun isMeditationAvailable(meditation: MeditationDto) =
        meditation.file_female_voice.isNotEmpty() || meditation.file_male_voice.isNotEmpty()

    private fun observeViewState(parentFragment: Fragment?) {
        (parentFragment?.view as? ViewGroup)?.findViewWithTag<View>("playList")?.let {
            viewBehavior = BottomSheetBehavior.from(it)
            viewBehavior?.addBottomSheetCallback(object :
                BottomSheetBehavior.BottomSheetCallback() {
                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                }

                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    when (newState) {
                        BottomSheetBehavior.STATE_EXPANDED -> {

                        }
                        BottomSheetBehavior.STATE_HALF_EXPANDED -> {

                        }
                        else -> {
                        }
                    }
                }
            })
        }
    }
}