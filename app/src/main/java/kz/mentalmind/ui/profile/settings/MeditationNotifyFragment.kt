package kz.mentalmind.ui.profile.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.akexorcist.snaptimepicker.SnapTimePickerDialog
import kz.mentalmind.R

class MeditationNotifyFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_meditation_notify, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        SnapTimePickerDialog.Builder().apply {
            setThemeColor(R.color.colorAccent)
            setTitleColor(R.color.white)
        }.build().show(childFragmentManager, MeditationNotifyFragment::class.simpleName)
    }

    companion object {

    }
}