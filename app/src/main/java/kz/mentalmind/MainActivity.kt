package kz.mentalmind

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.NonNull
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kz.mentalmind.ui.authorization.AuthActivity
import kz.mentalmind.ui.create.CreateFragment
import kz.mentalmind.ui.instruments.InstrumentsFragment
import kz.mentalmind.ui.main.MainFragment
import kz.mentalmind.ui.profile.ProfileFragment
import kz.mentalmind.utils.LoadingDialogFragment


class MainActivity : AppCompatActivity() {
    private val loadingDialog by lazy {
        LoadingDialogFragment(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpActionBar()
        val navigation = findViewById<BottomNavigationView>(R.id.navigation)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        replaceFragment(MainFragment())
    }

    override fun onStart() {
        super.onStart()
        hideActionBar()
    }

    override fun onStop() {
        super.onStop()
        showActionBar()
    }

    private val mOnNavigationItemSelectedListener: BottomNavigationView.OnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.getItemId()) {
                R.id.nav_home -> {
                    replaceFragment(MainFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_instruments -> {
                    replaceFragment(InstrumentsFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_create -> {
                    replaceFragment(CreateFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_profile -> {
                    replaceFragment(ProfileFragment())
                    return@OnNavigationItemSelectedListener true
                }
            }
            return@OnNavigationItemSelectedListener false
        }

    fun replaceFragment(fragment: Fragment, tag: String? = null) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        if (!tag.isNullOrEmpty()) {
            transaction.addToBackStack(tag)
        }
        transaction.commit()
    }

    fun alertDialog(context: Context, message: String?) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Предупреждение")
        builder.setMessage(message)
        builder.setPositiveButton("Ок") { dialog, wich ->
            dialog.cancel()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun setUpActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        supportActionBar?.title = title
        supportActionBar?.elevation = 0F
        val text: Spannable = SpannableString(supportActionBar?.title)
        text.setSpan(
            ForegroundColorSpan(getColor(R.color.colorPrimary)),
            0,
            text.length,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        supportActionBar?.title = text
        window.statusBarColor = resources.getColor(R.color.colorPrimary)
        supportActionBar?.setBackgroundDrawable(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.action_bar,
                null
            )
        )
    }

    private fun setUpActionBarWhiteStyle() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        supportActionBar?.title = title
        supportActionBar?.elevation = 0F
        val text: Spannable = SpannableString(supportActionBar?.title)
        text.setSpan(
            ForegroundColorSpan(getColor(R.color.colorPrimary)),
            0,
            text.length,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        supportActionBar?.title = text
        window.statusBarColor = resources.getColor(R.color.colorPrimary)
        supportActionBar?.setBackgroundDrawable(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.action_bar,
                null
            )
        )
    }

    fun openAuthorizationActivity() {
        val intent = Intent(this, AuthActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }

    fun hideKeyBoard() {
        val view: View? = this.currentFocus
        if (view != null) {
            val inputManager: InputMethodManager =
                this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(
                view.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

    fun hideActionBar() {
        supportActionBar?.hide()
    }

    fun showActionBar() {
        supportActionBar?.show()
    }

    fun showProgress() {
        loadingDialog.showLoading()
    }

    fun dismissProgress() {
        loadingDialog.hideLoading()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}