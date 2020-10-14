package kz.mentalmind.ui.main.mood

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_mood.*
import kz.mentalmind.MainActivity
import kz.mentalmind.R
import kz.mentalmind.data.MoodData

class MoodFragment : Fragment(), MoodResultListener {

    private lateinit var moodAdapter: MoodAdapter
    private val moodList: ArrayList<MoodData> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mood, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnBack.setOnClickListener { (activity as? MainActivity)?.onBackPressed() }
        setAdapter()
    }

    private fun setList(){
        val trevozhnyi = MoodData(1, ResourcesCompat.getDrawable(resources, R.drawable.trevozhnyi, null), "Тревожный")
        val uverennyi = MoodData(2, ResourcesCompat.getDrawable(resources, R.drawable.uverennyi, null), "Уверенный")
        val razdrazhennyi = MoodData(3, ResourcesCompat.getDrawable(resources, R.drawable.razdrazhennyi, null), "Раздраженный")
        val spokoinyi = MoodData(4, ResourcesCompat.getDrawable(resources, R.drawable.spokoinyi, null), "Спокойный")
        val zloi = MoodData(5, ResourcesCompat.getDrawable(resources, R.drawable.zloy, null), "Злой")
        val dobryi = MoodData(6, ResourcesCompat.getDrawable(resources, R.drawable.dobryi, null), "Добрый")
        val odinokiy = MoodData(7, ResourcesCompat.getDrawable(resources, R.drawable.odinokiy, null), "Одинокий")
        val lubimyi = MoodData(8, ResourcesCompat.getDrawable(resources, R.drawable.lubimyi, null), "Любимый")
        val ustavshiy = MoodData(9, ResourcesCompat.getDrawable(resources, R.drawable.ustavshiy, null), "Уставший")
        val energechnyi = MoodData(10, ResourcesCompat.getDrawable(resources, R.drawable.energichnyi, null), "Энергичный")
        val napryazhennyi = MoodData(11, ResourcesCompat.getDrawable(resources, R.drawable.napryazhennyi, null), "Напряженный")
        val umirotvorennyi = MoodData(12, ResourcesCompat.getDrawable(resources, R.drawable.umirotvorennyi, null), "Умиротворенный")
        val podavlennyi = MoodData(13, ResourcesCompat.getDrawable(resources, R.drawable.podavlennyi, null), "Подавленный")
        val sobrannyi = MoodData(14, ResourcesCompat.getDrawable(resources, R.drawable.sobrannyi, null), "Собранный")
        val rasstroennyi = MoodData(15, ResourcesCompat.getDrawable(resources, R.drawable.rasstroennyi, null), "Расстроенный")
        val dovolnyi = MoodData(16, ResourcesCompat.getDrawable(resources, R.drawable.dovolnyi, null), "Довольный")
        val nikakoi = MoodData(17, ResourcesCompat.getDrawable(resources, R.drawable.nikakoy, null), "Никакой")
        val schastlivyi = MoodData(18, ResourcesCompat.getDrawable(resources, R.drawable.schastlivyi, null), "Счастливый")
        moodList.addAll(listOf(trevozhnyi, uverennyi, razdrazhennyi, spokoinyi, zloi, dobryi, odinokiy, lubimyi, ustavshiy, energechnyi, napryazhennyi, umirotvorennyi, podavlennyi, sobrannyi, rasstroennyi, dovolnyi, nikakoi, schastlivyi))
    }

    private fun setAdapter() {
        setList()
        moodAdapter = MoodAdapter(moodList)
        rvMood.adapter = moodAdapter
        rvMood.layoutManager = GridLayoutManager(context, 2)
        moodAdapter.setMoodResultListener(this)
    }

    override fun onMoodClickedResult(moodData: MoodData) {
        (activity as? MainActivity)?.onBackPressed()
    }
}