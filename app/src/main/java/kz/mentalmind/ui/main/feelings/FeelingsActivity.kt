package kz.mentalmind.ui.main.feelings

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_mood.*
import kz.mentalmind.R
import kz.mentalmind.data.dto.FeelingDto
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
            FeelingDto(
                1,
                ResourcesCompat.getDrawable(resources, R.drawable.trevozhnyi, null),
                "Тревожный"
            ), FeelingDto(
                2,
                ResourcesCompat.getDrawable(resources, R.drawable.uverennyi, null),
                "Уверенный"
            ),
            FeelingDto(
                3,
                ResourcesCompat.getDrawable(resources, R.drawable.razdrazhennyi, null),
                "Раздраженный"
            ),
            FeelingDto(
                4,
                ResourcesCompat.getDrawable(resources, R.drawable.spokoinyi, null),
                "Спокойный"
            ),
            FeelingDto(5, ResourcesCompat.getDrawable(resources, R.drawable.zloy, null), "Злой"),
            FeelingDto(6, ResourcesCompat.getDrawable(resources, R.drawable.dobryi, null), "Добрый"),
            FeelingDto(
                7,
                ResourcesCompat.getDrawable(resources, R.drawable.odinokiy, null),
                "Одинокий"
            ),
            FeelingDto(8, ResourcesCompat.getDrawable(resources, R.drawable.lubimyi, null), "Любимый"),
            FeelingDto(
                9,
                ResourcesCompat.getDrawable(resources, R.drawable.ustavshiy, null),
                "Уставший"
            ),
            FeelingDto(
                10,
                ResourcesCompat.getDrawable(resources, R.drawable.energichnyi, null),
                "Энергичный"
            ),
            FeelingDto(
                11,
                ResourcesCompat.getDrawable(resources, R.drawable.napryazhennyi, null),
                "Напряженный"
            ),
            FeelingDto(
                12,
                ResourcesCompat.getDrawable(resources, R.drawable.umirotvorennyi, null),
                "Умиротворенный"
            ),
            FeelingDto(
                13,
                ResourcesCompat.getDrawable(resources, R.drawable.podavlennyi, null),
                "Подавленный"
            ),
            FeelingDto(
                14,
                ResourcesCompat.getDrawable(resources, R.drawable.sobrannyi, null),
                "Собранный"
            ),
            FeelingDto(
                15,
                ResourcesCompat.getDrawable(resources, R.drawable.rasstroennyi, null),
                "Расстроенный"
            ),
            FeelingDto(
                16,
                ResourcesCompat.getDrawable(resources, R.drawable.dovolnyi, null),
                "Довольный"
            ),
            FeelingDto(
                17,
                ResourcesCompat.getDrawable(resources, R.drawable.nikakoy, null),
                "Никакой"
            ),
            FeelingDto(
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

    override fun onMoodClickedResult(feeling: FeelingDto) {
        val intent = Intent()
        intent.putExtra(Constants.FEELING, feeling.id)
        setResult(RESULT_OK, intent)
        finish()
    }
}