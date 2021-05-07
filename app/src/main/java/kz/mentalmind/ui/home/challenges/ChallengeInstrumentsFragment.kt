package kz.mentalmind.ui.home.challenges

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_challenge_instruments.*
import kotlinx.android.synthetic.main.fragment_challenge_instruments.back
import kotlinx.android.synthetic.main.fragment_challenge_instruments.title
import kz.mentalmind.MainActivity
import kz.mentalmind.R
import kz.mentalmind.data.dto.Collection
import kz.mentalmind.ui.home.MainViewModel
import kz.mentalmind.ui.home.instruments.InstrumentClickListener
import kz.mentalmind.ui.meditations.MeditationsFragment
import kz.mentalmind.utils.Constants.CHALLENGE_ID
import org.koin.androidx.viewmodel.ext.android.viewModel


class ChallengeInstrumentsFragment : Fragment() {
    private val compositeDisposable = CompositeDisposable()
    private val viewModel: MainViewModel by viewModel()
    private var challengeId: Int = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as? MainActivity)?.hideBottomNavigation()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            challengeId = it.getInt(CHALLENGE_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_challenge_instruments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        compositeDisposable.add(viewModel.observeChallengeDetails().subscribe({
//            title.text = it.data?.name
            instruments.adapter = ChallengeInstrumentsAdapter(
                it.data?.collections ?: emptyList(),
                object : InstrumentClickListener {
                    override fun onInstrumentClicked(meditation: Collection) {
                        (activity as MainActivity).replaceFragment(
                            MeditationsFragment.newInstance(meditation.id),
                            MeditationsFragment::class.simpleName
                        )
                    }
                }
            )
        }, {}))
        compositeDisposable.add(
            viewModel.observeErrorSubject().subscribe {
                (activity as? MainActivity)?.alertDialog(requireContext(), it)
            }
        )

        back.setOnClickListener {
            activity?.onBackPressed()
        }

        viewModel.getChallengeDetails(challengeId)
    }

    companion object {
        @JvmStatic
        fun newInstance(challengeId: Int) =
            ChallengeInstrumentsFragment().apply {
                arguments = Bundle().apply {
                    putInt(CHALLENGE_ID, challengeId)
                }
            }
    }
}