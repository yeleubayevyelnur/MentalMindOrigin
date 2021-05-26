package kz.mentalmind.ui.profile

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_profile.*
import kz.mentalmind.MainActivity
import kz.mentalmind.R
import kz.mentalmind.data.dto.Meditation
import kz.mentalmind.data.entrance.User
import kz.mentalmind.ui.meditations.MeditationClickListener
import kz.mentalmind.ui.meditations.MeditationsAdapter
import kz.mentalmind.ui.player.PlayerActivity
import kz.mentalmind.ui.profile.settings.*
import kz.mentalmind.ui.purchase.TariffsAgreementFragment
import kz.mentalmind.utils.Constants
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*


class ProfileFragment : Fragment() {
    private val profileViewModel: ProfileViewModel by viewModel()
    private val compositeDisposable = CompositeDisposable()

    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (profileViewModel.getUser() != null) {
            user = profileViewModel.getUser()!!
            when {
                user.is_paid -> {
                    tvUnblock.text = "Ваш тариф: ${user.tariff?.name}"
                    tvSecondaryUnblock.text = "Действительно до: ${parseDate(user.subs_expiry_date)}"
                    tvSecondaryUnblock.visibility = View.VISIBLE
                    btnBuy.visibility = View.GONE
                }
                user.is_premium -> {
                    tvUnblock.text = "Ваш тариф: Премиум аккаунт"
                    btnBuy.visibility = View.GONE
                }
                else -> {
                    tvSecondaryUnblock.visibility = View.GONE
                    btnBuy.visibility = View.VISIBLE
                    tvUnblock.text = getString(R.string.unblock_opportunities)
                }
            }

        }
        profileViewModel.getToken()?.let {
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
//        changeLanguage.setOnClickListener {
//            (activity as? MainActivity)?.replaceFragment(
//                ChangeLanguageFragment(),
//                ChangeLanguageFragment::class.simpleName
//            )
//        }
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
        switchNotify.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) (activity as? MainActivity)?.replaceFragment(
                MeditationNotifyFragment(),
                MeditationNotifyFragment::class.simpleName
            )
        }
        val calendar = Calendar.getInstance()
        calendarView.maxDate = calendar.timeInMillis
        var calendarDate = ""
        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            calendarView.date = calendar.timeInMillis
            calendarDate = "$year-$month-$dayOfMonth"
            Log.d("yel", calendarDate)
        }

        btnShow.setOnClickListener {
            (activity as? MainActivity)?.replaceFragment(
                HistoryFragment.newInstance(
                    calendarDate
                ), HistoryFragment::class.simpleName
            )
        }
        btnBuy.setOnClickListener {
            (activity as? MainActivity)?.replaceFragment(
                TariffsAgreementFragment(),
                TariffsAgreementFragment::class.simpleName
            )
        }
    }

    private fun observeData(token: String) {
        compositeDisposable.add(
            profileViewModel.observeLevelDetailSubject().subscribe({
                if (it.error == null) {
                    Glide.with(requireContext()).load(it.levelsDetailData.file_image).into(ivLevel)
                    tvLevel.text = it.levelsDetailData.name
                    countDay.text =
                        String.format("%s дней", it.levelsDetailData.days_with_us.toString())
                    countTime.text =
                        String.format("%s минут", it.levelsDetailData.listened_minutes.toString())
                } else {
                    profileViewModel.observeErrorSubject().subscribe { error ->
                        (activity as? MainActivity)?.alertDialog(requireContext(), error)
                    }
                }
            }, {
            })
        )
        compositeDisposable.add(
            profileViewModel.observeProfileSubject().subscribe({
                tvEmail.text = it.profileData.email
                it.profileData.profile_image?.let { profileUrl ->
                    Glide.with(requireContext()).load(profileUrl)
                        .into(ivProfilePhoto)
                }
                profileViewModel.getLevelDetail(token, it.profileData.level)
                val adapter = MeditationsAdapter(
                    it.profileData.favorite_meditations,
                    object : MeditationClickListener {
                        override fun onMeditationClicked(meditation: Meditation) {
                            startActivity(
                                Intent(
                                    requireActivity(),
                                    PlayerActivity::class.java
                                ).apply {
                                    putExtra(Constants.MEDITATION, meditation)
                                })
                        }
                    })
                favMeditations.adapter = adapter
            }, {
            })
        )
    }

    private fun parseDate(dateValue: String?): String? {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSZ", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
        val date = inputFormat.parse(dateValue)
        return outputFormat.format(date)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as? MainActivity)?.showBottomNavigation()
    }

    override fun onDetach() {
        super.onDetach()
        (activity as? MainActivity)?.hideBottomNavigation()
    }
}