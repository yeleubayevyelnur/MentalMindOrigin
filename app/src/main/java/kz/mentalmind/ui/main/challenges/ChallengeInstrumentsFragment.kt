package kz.mentalmind.ui.main.challenges

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kz.mentalmind.R


class ChallengeInstrumentsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_challenge_instruments, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ChallengeInstrumentsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}