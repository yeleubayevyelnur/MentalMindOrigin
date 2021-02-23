package kz.mentalmind.ui.creative

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_affirmation.*
import kz.mentalmind.MainActivity
import kz.mentalmind.R
import kz.mentalmind.data.dto.Affirmation
import kz.mentalmind.utils.Constants.AFFIRMATION

class AffirmationFragment : Fragment() {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as? MainActivity)?.hideBottomNavigation()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_affirmation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        back.setOnClickListener {
            activity?.onBackPressed()
        }
        arguments?.getParcelable<Affirmation>(AFFIRMATION)?.let { affirmation ->

            share.setOnClickListener {
            }

            Glide.with(requireContext())
                .load(affirmation.file_image)
                .into(ivBanner)

            tvTitle.text = affirmation.name
            tvDescription.text = affirmation.description
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(affirmation: Affirmation) =
            AffirmationFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(AFFIRMATION, affirmation)
                }
            }
    }
}