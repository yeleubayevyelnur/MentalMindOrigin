package kz.mentalmind.ui.meditations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_play_list.*
import kz.mentalmind.R
import kz.mentalmind.domain.dto.CollectionDetailsDto


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

    fun setData(data: CollectionDetailsDto) {
        title.text = data.name
        description.text = data.description
        val adapter = MeditationsAdapter(data.meditations)
        meditations.adapter = adapter
    }

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