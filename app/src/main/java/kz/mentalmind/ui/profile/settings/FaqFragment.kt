package kz.mentalmind.ui.profile.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import kotlinx.android.synthetic.main.fragment_faq.*
import kz.mentalmind.MainActivity
import kz.mentalmind.R

class FaqFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_faq, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        about.setOnClickListener {
            setVisibility(aboutDescription)
        }
        whatIsInstruments.setOnClickListener {
            setVisibility(aboutInstruments)
        }
        howLong.setOnClickListener {
            setVisibility(aboutHowLong)
        }
        whatResult.setOnClickListener {
            setVisibility(aboutResult)
        }
        btnBack.setOnClickListener {
            (activity as? MainActivity)?.onBackPressed()
        }
    }

    private fun setVisibility(view: AppCompatTextView) {
        when (view.visibility) {
            View.GONE -> {
                view.visibility = View.VISIBLE
            }
            View.VISIBLE -> {
                view.visibility = View.GONE
            }
            View.INVISIBLE -> {
            }
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

    companion object {

    }
}