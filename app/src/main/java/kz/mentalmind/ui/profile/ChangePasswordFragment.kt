package kz.mentalmind.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kz.mentalmind.MainActivity
import kz.mentalmind.R

class ChangePasswordFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_change_password, container, false)
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