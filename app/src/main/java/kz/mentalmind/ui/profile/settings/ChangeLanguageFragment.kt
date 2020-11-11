package kz.mentalmind.ui.profile.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_change_language.*
import kz.mentalmind.MainActivity
import kz.mentalmind.R

class  ChangeLanguageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_change_language, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

    companion object {

    }
}