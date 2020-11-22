package kz.mentalmind.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_levels.*
import kz.mentalmind.MainActivity
import kz.mentalmind.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class LevelsFragment : Fragment() {

    private val profileViewModel: ProfileViewModel by viewModel()
    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_levels, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        compositeDisposable.add(
            profileViewModel.observeErrorSubject().subscribe { error ->
                (activity as? MainActivity)?.alertDialog(requireContext(), error)
            }
        )
        compositeDisposable.add(
            profileViewModel.observeLevelsSubject().subscribe({
                rvLevels.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                rvLevels.adapter = LevelsAdapter(it.levelsData.results)
            }, {

            })
        )
        profileViewModel.getToken()?.let {
            profileViewModel.getLevels(it)
        }
        btnBack.setOnClickListener {
            (activity as? MainActivity)?.onBackPressed()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as? MainActivity)?.hideBottomNavigation()
    }

    override fun onDetach() {
        super.onDetach()
        (activity as? MainActivity)?.showBottomNavigation()
    }
}