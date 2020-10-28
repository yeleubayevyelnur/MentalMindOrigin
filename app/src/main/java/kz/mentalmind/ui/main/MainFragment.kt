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
import kz.mentalmind.data.CollectionItem
import kz.mentalmind.domain.dto.CourseDto
import kz.mentalmind.ui.main.mood.MoodActivity
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
        }
        getCollectionsByFeeling()

        moodView.setOnClickListener {
            startActivityForResult(
                Intent(requireActivity(), MoodActivity::class.java), moodRequestCode
            )
        }
    }

    private fun observeData() {
        compositeDisposable.add(
            viewModel.observeStreamOfLife().subscribe({
                rvStreamOfLife.adapter = InstrumentsAdapter(
                    it.data.results,
                    object : InstrumentClickListener {
                        override fun onInstrumentClicked(meditation: CollectionItem) {
                            (activity as MainActivity).replaceFragment(
                                MeditationsFragment.newInstance(meditation.id),
                                MeditationsFragment::class.simpleName
                            )
                        }
                    }
                )
            }, {

            })
        )

        compositeDisposable.add(
            viewModel.observeInstrumentsForFeeling().subscribe({
                if (it.data.results.isNullOrEmpty()) {
                    rvRecommended.visibility = View.GONE
                    tvRecommended.visibility = View.GONE
                    return@subscribe
                }

                rvRecommended.adapter = InstrumentsAdapter(
                    it.data.results,
                    object : InstrumentClickListener {
                        override fun onInstrumentClicked(meditation: CollectionItem) {
                            (activity as MainActivity).replaceFragment(
                                MeditationsFragment.newInstance(meditation.id),
                                MeditationsFragment::class.simpleName
                            )
                        }
                    }
                )
                rvRecommended.visibility = View.VISIBLE
                tvRecommended.visibility = View.VISIBLE
            }, {

            })
        )
        compositeDisposable.add(
            viewModel.observeErrorSubject().subscribe {
                (activity as? MainActivity)?.alertDialog(requireContext(), it)
            }
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
                    override fun onCourseClicked(course: CourseDto) {
                        val i = Intent(Intent.ACTION_VIEW)
                        i.data = Uri.parse(course.url)
                        startActivity(i)
                    }
                }
            )
            tvOnlineEducationTitle.visibility = View.VISIBLE
            rvOnlineEducation.visibility = View.VISIBLE
        }, {}))
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