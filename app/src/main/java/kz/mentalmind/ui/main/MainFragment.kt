package kz.mentalmind.ui.main

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_main.*
import kz.mentalmind.MainActivity
import kz.mentalmind.R
import kz.mentalmind.data.dto.Challenge
import kz.mentalmind.data.dto.Collection
import kz.mentalmind.data.dto.Course
import kz.mentalmind.data.dto.FavoriteMeditation
import kz.mentalmind.ui.main.challenges.ChallengeClickListener
import kz.mentalmind.ui.main.challenges.ChallengeInstrumentsFragment
import kz.mentalmind.ui.main.challenges.ChallengesAdapter
import kz.mentalmind.ui.main.courses.CourseClickListener
import kz.mentalmind.ui.main.courses.CoursesAdapter
import kz.mentalmind.ui.main.favorites.FavoriteClickListener
import kz.mentalmind.ui.main.favorites.FavoritesAdapter
import kz.mentalmind.ui.main.feelings.FeelingsActivity
import kz.mentalmind.ui.main.instruments.InstrumentClickListener
import kz.mentalmind.ui.main.instruments.InstrumentsAdapter
import kz.mentalmind.ui.meditations.MeditationsFragment
import kz.mentalmind.utils.Constants
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {
    private val moodRequestCode = 777
    private val viewModel: MainViewModel by viewModel()
    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        viewModel.getToken()?.let {
            viewModel.getStreamOfLife(it)
            viewModel.getCourses(it)
            viewModel.getChallenges(it)
            viewModel.getFavorites(it)
        }
        getCollectionsByFeeling()

        moodView.setOnClickListener {
            startActivityForResult(
                Intent(requireActivity(), FeelingsActivity::class.java), moodRequestCode
            )
        }
    }

    private fun observeData() {
        compositeDisposable.add(
            viewModel.observeStreamOfLife().subscribe({
                it.data?.results?.let { collections ->
                    rvStreamOfLife.adapter = InstrumentsAdapter(
                        collections,
                        object : InstrumentClickListener {
                            override fun onInstrumentClicked(meditation: Collection) {
                                (activity as MainActivity).replaceFragment(
                                    MeditationsFragment.newInstance(meditation.id),
                                    MeditationsFragment::class.simpleName
                                )
                            }
                        }
                    )
                }

            }, {

            })
        )

        compositeDisposable.add(
            viewModel.observeInstrumentsForFeeling().subscribe({
                val collections = it.data?.results
                when {
                    collections.isNullOrEmpty() -> {
                        rvRecommended.visibility = View.GONE
                        tvRecommended.visibility = View.GONE
                    }
                    else -> {
                        rvRecommended.adapter = InstrumentsAdapter(
                            collections,
                            object : InstrumentClickListener {
                                override fun onInstrumentClicked(meditation: Collection) {
                                    (activity as MainActivity).replaceFragment(
                                        MeditationsFragment.newInstance(meditation.id),
                                        MeditationsFragment::class.simpleName
                                    )
                                }
                            }
                        )
                        rvRecommended.visibility = View.VISIBLE
                        tvRecommended.visibility = View.VISIBLE
                    }
                }
            }, {

            })
        )
        compositeDisposable.add(
            viewModel.observeErrorSubject().subscribe {
                (activity as? MainActivity)?.alertDialog(requireContext(), it)
            }
        )

        compositeDisposable.add(
            viewModel.observeChallengesResponse().subscribe({
                if (it.isNullOrEmpty()) {
                    tvChallenges.visibility = View.GONE
                    rvChallenges.visibility = View.GONE
                    challengesTopDivider.visibility = View.GONE
                    challengesBottomDivider.visibility = View.GONE
                } else {
                    rvChallenges.adapter = ChallengesAdapter(it, object : ChallengeClickListener {
                        override fun onChallengeClicked(challenge: Challenge) {
                            (activity as? MainActivity)?.replaceFragment(
                                ChallengeInstrumentsFragment.newInstance(challenge.id),
                                ChallengeInstrumentsFragment::class.simpleName
                            )
                        }
                    })
                    tvChallenges.visibility = View.VISIBLE
                    rvChallenges.visibility = View.VISIBLE
                    challengesTopDivider.visibility = View.VISIBLE
                    challengesBottomDivider.visibility = View.VISIBLE
                }
            }, {})
        )

        compositeDisposable.add(viewModel.observeCourses().subscribe({
            if (it.isNullOrEmpty()) {
                tvOnlineEducationTitle.visibility = View.GONE
                rvOnlineEducation.visibility = View.GONE
                return@subscribe
            }
            rvOnlineEducation.adapter = CoursesAdapter(
                it,
                object : CourseClickListener {
                    override fun onCourseClicked(course: Course) {
                        val i = Intent(Intent.ACTION_VIEW)
                        i.data = Uri.parse(course.url)
                        startActivity(i)
                    }
                }
            )
            tvOnlineEducationTitle.visibility = View.VISIBLE
            rvOnlineEducation.visibility = View.VISIBLE
        }, {}))

        compositeDisposable.add(viewModel.observeFavorites().subscribe({
            val collections = it.data?.results
            when {
                collections.isNullOrEmpty() -> {
                    rvFavorites.visibility = View.GONE
                    tvFavorites.visibility = View.GONE
                }
                else -> {
                    rvFavorites.adapter = FavoritesAdapter(
                        collections,
                        object : FavoriteClickListener {
                            override fun onFavoriteClicked(meditation: FavoriteMeditation) {
                                (activity as MainActivity).replaceFragment(
                                    MeditationsFragment.newInstance(meditation.collection_id),
                                    MeditationsFragment::class.simpleName
                                )
                            }
                        }
                    )
                    rvFavorites.visibility = View.VISIBLE
                    tvFavorites.visibility = View.VISIBLE
                }
            }
        }, {}))
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).showBottomNavigation()
    }

    private fun getCollectionsByFeeling() {
        viewModel.getToken()?.let {
            viewModel.getCollectionsByFeeling(it)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == moodRequestCode && resultCode == RESULT_OK) {
            viewModel.saveFeeling(data?.getIntExtra(Constants.FEELING, 9999) ?: 9999)
            getCollectionsByFeeling()
        }
    }
}