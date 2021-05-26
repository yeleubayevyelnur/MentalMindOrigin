package kz.mentalmind.ui.purchase

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_tariffs_agreement.*
import kz.mentalmind.MainActivity
import kz.mentalmind.R
import kz.mentalmind.utils.Constants

class TariffsAgreementFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tariffs_agreement, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        close.setOnClickListener { (activity as MainActivity).onBackPressed() }
        checkbox.setOnCheckedChangeListener { _, isChecked ->
            btnNext.isEnabled = isChecked
        }
        tvPrivacyPolicy.setOnClickListener {
            (activity as? MainActivity)?.replaceFragment(
                PaymentFragment.newInstance(Constants.PRIVACY_POLICY)
            )
        }
        btnNext.setOnClickListener {
            (activity as? MainActivity)?.replaceFragment(
                TariffsFragment(),
                TariffsFragment::class.simpleName
            )
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as? MainActivity)?.hideBottomNavigation()
    }

    override fun onResume() {
        super.onResume()
        (activity as? MainActivity)?.hideBottomNavigation()
    }
    companion object {

    }
}