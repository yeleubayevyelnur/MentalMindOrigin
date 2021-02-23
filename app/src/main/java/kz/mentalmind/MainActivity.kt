package kz.mentalmind

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kz.mentalmind.navigation.NavigationTab
import kz.mentalmind.ui.authorization.AuthActivity
import kz.mentalmind.ui.creative.CreativeFragment
import kz.mentalmind.ui.instruments.InstrumentsFragment
import kz.mentalmind.ui.main.HomeFragment
import kz.mentalmind.ui.profile.ProfileFragment
import kz.mentalmind.utils.LoadingDialogFragment
import org.koin.android.ext.android.inject


class MainActivity : AppCompatActivity() {
    private val navigator = AppNavigator(this, R.id.container)
    private lateinit var bottomNavigation: BottomNavigationView
    private val navigatorHolder: NavigatorHolder by inject()
    private val loadingDialog by lazy {
        LoadingDialogFragment(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        setContentView(R.layout.activity_main)
        bottomNavigation = findViewById(R.id.navigation)
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        showFragment(NavigationTab.getByItemId(R.id.nav_home))
    }

    fun showBottomNavigation() {
        navigation.visibility = View.VISIBLE
    }

    fun hideBottomNavigation() {
        navigation.visibility = View.GONE
    }

    private val mOnNavigationItemSelectedListener: BottomNavigationView.OnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home, R.id.nav_instruments, R.id.nav_creative, R.id.nav_profile -> {
                    handleBottomNavigation(item.itemId, item.isChecked)
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
        AlertDialog.Builder(context).apply {
            setTitle(getString(R.string.warning))
            setMessage(message)
            setPositiveButton(getString(R.string.okay)) { dialog, _ ->
                dialog.cancel()
            }
        }.create().show()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
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

    fun showProgress() {
        loadingDialog.showLoading()
    }

    fun dismissProgress() {
        loadingDialog.hideLoading()
    }

    fun logout() {

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun showFragment(nextTab: NavigationTab) {
        val currentTab = NavigationTab.getByItemId(bottomNavigation.selectedItemId)
        showFragment(currentTab, nextTab)
    }

    private fun showFragment(currentTab: NavigationTab, newTab: NavigationTab) {
        if (supportFragmentManager.fragments.isNotEmpty()) {
            val previousFragment = supportFragmentManager.findFragmentByTag(currentTab.fragmentTag)

            supportFragmentManager.beginTransaction()
                .apply {
                    if (previousFragment != null) {
                        this.hide(previousFragment)
                    }

                    val nextFragment = supportFragmentManager.findFragmentByTag(newTab.fragmentTag)
                    if (nextFragment != null) {
                        this.show(nextFragment)
                    } else {
                        this.add(R.id.container, newFragment(newTab), newTab.fragmentTag)
                    }
                }
                .commit()
        } else {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, newFragment(newTab), newTab.fragmentTag)
                .commit()
        }
    }

    private fun handleBottomNavigation(resId: Int, isChecked: Boolean) {
        if (!isChecked) {
            showFragment(NavigationTab.getByItemId(resId))
        }
    }

    private fun newFragment(tab: NavigationTab): Fragment {
        return when (tab) {
            NavigationTab.HOME -> HomeFragment()
            NavigationTab.INSTRUMENTS -> InstrumentsFragment()
            NavigationTab.CREATIVE -> CreativeFragment()
            NavigationTab.PROFILE -> ProfileFragment()
        }
    }
}