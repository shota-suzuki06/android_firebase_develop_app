package shota.suzuki.android_firebase_develop_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home.*

enum class ProviderType {
    BASIC
}

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Setup
        val bundle = intent.extras
        val email = bundle?.getString("email")
        val provider = bundle?.getString("provider")
        setUp(email ?: "", provider ?: "")
    }

    private fun setUp(email: String, provider: String) {
        title = "home"
        textEmailId.text = email
        textProviderId.text = provider

        btnLogoutId.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }

        btnNextId.setOnClickListener {
            val intent = Intent(this, WriteActivity::class.java)
            startActivity(intent)
        }

    }
}
