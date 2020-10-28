package kz.mentalmind.ui.onboarding.splashscreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kz.mentalmind.R
import kz.mentalmind.ui.authorization.AuthActivity
import kz.mentalmind.ui.onboarding.OnBoardingActivity

class SplashScreenActivity : AppCompatActivity() {
    private val timeout = 1000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        val pref = this.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        if (pref.getBoolean(IS_FIRST_TIME_LAUNCH, true)) {
            Handler().postDelayed({
                val intent = Intent(this, OnBoardingActivity::class.java)
                startActivity(intent)
                finish()
            }, timeout)
        } else {
            Handler().postDelayed({
                val intent = Intent(this, AuthActivity::class.java)
                startActivity(intent)
                finish()
            }, timeout)
        }

    }

    companion object {
        private const val IS_FIRST_TIME_LAUNCH = "IS_FIRST_TIME_LAUNCH"
        private const val PREF_NAME = "PREF_NAME"
    }
}