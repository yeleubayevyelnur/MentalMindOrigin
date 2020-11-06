package kz.mentalmind.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        profileViewModel.getToken()?.let {
            compositeDisposable.add(
                profileViewModel.observeLevelsSubject().subscribe({
                    rvLevels.adapter = LevelsAdapter(it.levelsData.results)
                }, {

                })
            )
            profileViewModel.getLevels(it)
        }
        btnBack.setOnClickListener {
            (activity as? MainActivity)?.onBackPressed()
        }
    }

    override fun onStart() {
        super.onStart()
        (activity as? MainActivity)?.hideBottomNavigation()
    }

    override fun onStop() {
        super.onStop()
        (activity as? MainActivity)?.showBottomNavigation()
    }
}