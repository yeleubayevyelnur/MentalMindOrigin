package kz.mentalmind.ui.authorization

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kz.mentalmind.MainActivity
import kz.mentalmind.R
import kz.mentalmind.ui.authorization.login.LoginFragment


class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = resources.getColor(R.color.colorPrimary)
        setContentView(R.layout.activity_auth)
        replaceFragment(LoginFragment())
    }

    fun replaceFragment(fragment: Fragment, tag: String? = null) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.authContainer, fragment)
        if (!tag.isNullOrEmpty()) {
            transaction.addToBackStack(tag)
        }
        transaction.commit()
    }

    fun openMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }

    fun successDialog(context: Context, title: String?, message: String?) {
        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Ок") { dialog, wich ->
                openMainActivity()
                dialog.cancel()
            }
            .create().show()
    }

    fun alertDialog(context: Context, message: String?) {
        AlertDialog.Builder(context)
            .setTitle("Предупреждение")
            .setMessage(message)
            .setPositiveButton("Ок") { dialog, wich ->
                dialog.cancel()
            }
            .create().show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}