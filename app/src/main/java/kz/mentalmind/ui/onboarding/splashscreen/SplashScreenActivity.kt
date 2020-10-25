package kz.mentalmind.ui.onboarding.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kz.mentalmind.R
import kz.mentalmind.ui.onboarding.OnBoardingActivity

class SplashScreenActivity : AppCompatActivity() {
    private val timeout = 1000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Handler().postDelayed({
            val intent = Intent(this, OnBoardingActivity::class.java)
            startActivity(intent)
            finish()
        }, timeout)
    }
}