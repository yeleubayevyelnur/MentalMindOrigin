package kz.mentalmind.ui.purchase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_tariffs.*
import kz.mentalmind.MainActivity
import kz.mentalmind.R

class TrialFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tariffs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        close.setOnClickListener {
            (activity as? MainActivity)?.onBackPressed()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TrialFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}