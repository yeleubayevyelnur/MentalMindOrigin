package kz.mentalmind.ui.meditations

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_player.*
import kz.mentalmind.MainActivity
import kz.mentalmind.R
import kz.mentalmind.utils.Constants.ID
import org.koin.androidx.viewmodel.ext.android.viewModel

class MeditationsFragment : Fragment() {
    private val meditationsViewModel: MeditationsViewModel by viewModel()
    private val compositeDisposable = CompositeDisposable()

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
            playListFragment?.setData(it)
        }, {}))
        meditationsViewModel.getToken(requireContext())
            ?.let { meditationsViewModel.getCollectionDetails(it, requireArguments().getInt(ID)) }

        back.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    companion object {
        fun newInstance(id: Int) = MeditationsFragment().apply {
            arguments = Bundle().apply { putInt(ID, id) }
        }
    }
}