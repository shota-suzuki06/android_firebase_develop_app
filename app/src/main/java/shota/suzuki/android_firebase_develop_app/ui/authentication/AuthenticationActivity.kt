package shota.suzuki.android_firebase_develop_app.ui.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import shota.suzuki.android_firebase_develop_app.R
import shota.suzuki.android_firebase_develop_app.ui.dashboard.DashboardActivity
import shota.suzuki.android_firebase_develop_app.ui.storage.StorageActivity

class AuthenticationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)

        setup()
    }

    private fun setup() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.selectedItemId = R.id.authentication
        bottomNav.setOnNavigationItemSelectedListener {
            println(it.toString())
            println(R.id.storage.toString())
            when (it.itemId) {
                R.id.dashboard -> {
                    startActivity( Intent(this, DashboardActivity::class.java) )
                    finish()
                }
                R.id.storage -> {
                    startActivity( Intent(this, StorageActivity::class.java) )
                    finish()
                }
            }
            true
        }
    }
}
