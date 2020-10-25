package kz.mentalmind.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_profile.*
import kz.mentalmind.MainActivity
import kz.mentalmind.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {
    private val profileViewModel: ProfileViewModel by viewModel()
    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        profileViewModel.getToken(requireContext())?.let { profileViewModel.getProfile(it) }
        info.setOnClickListener {
            (activity as? MainActivity)?.replaceFragment(
                LevelsFragment(),
                LevelsFragment::class.simpleName
            )
        }

        ivSettings.setOnClickListener {
            (activity as? MainActivity)?.replaceFragment(
                SettingsFragment(),
                SettingsFragment::class.simpleName
            )
        }
        faq.setOnClickListener {
            (activity as? MainActivity)?.replaceFragment(
                FaqFragment(),
                FaqFragment::class.simpleName
            )
        }
    }

    private fun observeData() {
        compositeDisposable.add(
            profileViewModel.observeProfileSubject().subscribe({
                profileViewModel.getLevelDetail(it.profileData.level)
                tvEmail.text = it.profileData.email
            }, {

            })
        )
        compositeDisposable.add(
            profileViewModel.observeLevelDetailSubject().subscribe({
                Glide.with(requireContext()).load(it.levelsDetailData.file_image).into(ivLevel)
                tvLevel.text = it.levelsDetailData.name
                countDay.text =
                    String.format("%s дней", it.levelsDetailData.days_with_us.toString())
                countTime.text =
                    String.format("%s минут", it.levelsDetailData.listened_minutes.toString())
            }, {

            })
        )
    }

    companion object {

    }
}