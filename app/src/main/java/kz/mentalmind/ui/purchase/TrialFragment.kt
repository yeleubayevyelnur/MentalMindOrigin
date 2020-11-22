package kz.mentalmind.ui.purchase

import android.content.Context
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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

        val textShader1 = LinearGradient(
            0F,
            0F,
            0F,
            20F,
            intArrayOf(
                ContextCompat.getColor(requireContext(), R.color.gr1),
                ContextCompat.getColor(requireContext(), R.color.gr2)
            ),
            floatArrayOf(0F, 1F),
            Shader.TileMode.CLAMP
        )
        val textShader2 = LinearGradient(
            0F,
            0F,
            0F,
            20F,
            intArrayOf(
                ContextCompat.getColor(requireContext(), R.color.gr3),
                ContextCompat.getColor(requireContext(), R.color.gr4)
            ),
            floatArrayOf(0F, 1F),
            Shader.TileMode.CLAMP
        )
        btnSub1.paint.shader = textShader1
        btnSub2.paint.shader = textShader2
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as? MainActivity)?.hideBottomNavigation()
    }

    override fun onDetach() {
        super.onDetach()
        (activity as? MainActivity)?.showBottomNavigation()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            TrialFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}