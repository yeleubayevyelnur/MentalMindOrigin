package kz.mentalmind.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_on_boarding.*
import kz.mentalmind.R
import kz.mentalmind.data.IntroSlide
import kz.mentalmind.ui.authorization.AuthActivity

class OnBoardingActivity : AppCompatActivity() {
    private lateinit var introSliderAdapter: IntroSliderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        setContentView(R.layout.activity_on_boarding)
        introSliderAdapter = IntroSliderAdapter(
            listOf(
                IntroSlide(
                    getString(R.string.ob_first_main_text),
                    getString(R.string.ob_first_secondary_text)
                ),
                IntroSlide(
                    getString(R.string.ob_second_main_text),
                    getString(R.string.ob_second_secondary_text)
                ),
                IntroSlide(
                    getString(R.string.ob_third_main_text),
                    getString(R.string.ob_third_secondary_text)
                )
            )
        )
        introSliderViewPager.adapter = introSliderAdapter
        setIndicators()
        setCurrentIndicator(0)
        introSliderViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })
        btnNext.setOnClickListener {
            if (introSliderViewPager.currentItem + 1 < introSliderAdapter.itemCount) {
                introSliderViewPager.currentItem += 1
            } else {
                Intent(applicationContext, AuthActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
        btnBack.setOnClickListener {
            if (introSliderViewPager.currentItem == 0) {
                onBackPressed()
            } else {
                setCurrentIndicator(introSliderViewPager.currentItem - 1)
                introSliderViewPager.currentItem = introSliderViewPager.currentItem - 1
            }
        }
    }

    private fun setIndicators() {
        val indicators = arrayOfNulls<ImageView>(introSliderAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i].apply {
                this?.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext, R.drawable.indicator_inactive
                    )
                )
                this?.layoutParams = layoutParams
            }
            indicatorContainer.addView(indicators[i])
        }
    }

    private fun setCurrentIndicator(index: Int) {
        val childCount = indicatorContainer.childCount
        for (i in 0 until childCount) {
            val imageView = indicatorContainer[i] as ImageView
            if (i == index) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext, R.drawable.indicator_active
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext, R.drawable.indicator_inactive
                    )
                )
            }
        }
    }
}