package kz.mentalmind.ui.main.feelings

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_mood.*
import kz.mentalmind.R
import kz.mentalmind.data.Feeling
import kz.mentalmind.utils.Constants


class FeelingsActivity : AppCompatActivity(), FeelingResultListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mood)
        setAdapter()
        btnBack.setOnClickListener { onBackPressed() }
    }

    private fun setAdapter() {
        val feelingList = arrayListOf(
            Feeling(
                1,
                ResourcesCompat.getDrawable(resources, R.drawable.trevozhnyi, null),
                "Тревожный"
            ), Feeling(
                2,
                ResourcesCompat.getDrawable(resources, R.drawable.uverennyi, null),
                "Уверенный"
            ),
            Feeling(
                3,
                ResourcesCompat.getDrawable(resources, R.drawable.razdrazhennyi, null),
                "Раздраженный"
            ),
            Feeling(
                4,
                ResourcesCompat.getDrawable(resources, R.drawable.spokoinyi, null),
                "Спокойный"
            ),
            Feeling(5, ResourcesCompat.getDrawable(resources, R.drawable.zloy, null), "Злой"),
            Feeling(6, ResourcesCompat.getDrawable(resources, R.drawable.dobryi, null), "Добрый"),
            Feeling(
                7,
                ResourcesCompat.getDrawable(resources, R.drawable.odinokiy, null),
                "Одинокий"
            ),
            Feeling(8, ResourcesCompat.getDrawable(resources, R.drawable.lubimyi, null), "Любимый"),
            Feeling(
                9,
                ResourcesCompat.getDrawable(resources, R.drawable.ustavshiy, null),
                "Уставший"
            ),
            Feeling(
                10,
                ResourcesCompat.getDrawable(resources, R.drawable.energichnyi, null),
                "Энергичный"
            ),
            Feeling(
                11,
                ResourcesCompat.getDrawable(resources, R.drawable.napryazhennyi, null),
                "Напряженный"
            ),
            Feeling(
                12,
                ResourcesCompat.getDrawable(resources, R.drawable.umirotvorennyi, null),
                "Умиротворенный"
            ),
            Feeling(
                13,
                ResourcesCompat.getDrawable(resources, R.drawable.podavlennyi, null),
                "Подавленный"
            ),
            Feeling(
                14,
                ResourcesCompat.getDrawable(resources, R.drawable.sobrannyi, null),
                "Собранный"
            ),
            Feeling(
                15,
                ResourcesCompat.getDrawable(resources, R.drawable.rasstroennyi, null),
                "Расстроенный"
            ),
            Feeling(
                16,
                ResourcesCompat.getDrawable(resources, R.drawable.dovolnyi, null),
                "Довольный"
            ),
            Feeling(
                17,
                ResourcesCompat.getDrawable(resources, R.drawable.nikakoy, null),
                "Никакой"
            ),
            Feeling(
                18,
                ResourcesCompat.getDrawable(resources, R.drawable.schastlivyi, null),
                "Счастливый"
            )
        )

        val adapter = FeelingsAdapter(feelingList)
        adapter.setMoodResultListener(this)
        rvMood.adapter = adapter
        rvMood.layoutManager = GridLayoutManager(this, 2)
    }

    override fun onMoodClickedResult(feeling: Feeling) {
        val intent = Intent()
        intent.putExtra(Constants.FEELING, feeling.id)
        setResult(RESULT_OK, intent)
        finish()
    }
}