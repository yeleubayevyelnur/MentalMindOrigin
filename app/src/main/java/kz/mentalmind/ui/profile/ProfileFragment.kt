package kz.mentalmind.ui.profile

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_play_list.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kz.mentalmind.MainActivity
import kz.mentalmind.R
import kz.mentalmind.domain.dto.MeditationDto
import kz.mentalmind.ui.meditations.MeditationClickListener
import kz.mentalmind.ui.meditations.MeditationsAdapter
import kz.mentalmind.ui.player.PlayerActivity
import kz.mentalmind.ui.profile.settings.ChangeLanguageFragment
import kz.mentalmind.ui.profile.settings.FaqFragment
import kz.mentalmind.ui.profile.settings.HelpFragment
import kz.mentalmind.ui.profile.settings.PromocodeFragment
import kz.mentalmind.utils.Constants
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProfileFragment : Fragment() {
    private val profileViewModel: ProfileViewModel by viewModel()
    private val compositeDisposable = CompositeDisposable()
    private lateinit var meditationsAdapter: MeditationsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileViewModel.getToken(requireContext())?.let {
            observeData(it)
            profileViewModel.getProfile(it)
        }
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
        help.setOnClickListener {
            (activity as? MainActivity)?.replaceFragment(
                HelpFragment(),
                HelpFragment::class.simpleName
            )
        }
        changeLanguage.setOnClickListener {
            (activity as? MainActivity)?.replaceFragment(
                ChangeLanguageFragment(),
                ChangeLanguageFragment::class.simpleName
            )
        }
        addPromocode.setOnClickListener {
            (activity as? MainActivity)?.replaceFragment(
                PromocodeFragment(),
                PromocodeFragment::class.simpleName
            )
        }
        tvInstagram.setOnClickListener {
            val uri: Uri = Uri.parse("http://instagram.com/_u/mentalmind.kz")
            val likeIng = Intent(Intent.ACTION_VIEW, uri)
            likeIng.setPackage("com.instagram.android")
            try {
                startActivity(likeIng)
            } catch (e: ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://instagram.com/mentalmind.kz")
                    )
                )
            }
        }
    }

    private fun observeData(token: String) {
        compositeDisposable.add(
            profileViewModel.observeLevelDetailSubject().subscribe {
                Glide.with(requireContext()).load(it.levelsDetailData.file_image).into(ivLevel)
                tvLevel.text = it.levelsDetailData.name
                countDay.text =
                    String.format("%s дней", it.levelsDetailData.days_with_us.toString())
                countTime.text =
                    String.format("%s минут", it.levelsDetailData.listened_minutes.toString())
            }
        )
        compositeDisposable.add(
            profileViewModel.observeProfileSubject().subscribe {
                tvEmail.text = it.profileData.email
                profileViewModel.getLevelDetail(token, it.profileData.level)
                val adapter = MeditationsAdapter(
                    it.profileData.favorite_meditations,
                    object : MeditationClickListener {
                        override fun onMeditationClicked(meditation: MeditationDto) {
                            startActivity(
                                Intent(
                                    requireActivity(),
                                    PlayerActivity::class.java
                                ).apply {
                                    putExtra(Constants.MEDITATION, meditation)
                                })
                        }
                    })
                meditations.adapter = adapter
            })
    }

    companion object {

    }
}