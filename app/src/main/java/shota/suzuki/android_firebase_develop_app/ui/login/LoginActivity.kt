package shota.suzuki.android_firebase_develop_app.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*
import shota.suzuki.android_firebase_develop_app.R
import shota.suzuki.android_firebase_develop_app.ui.dashboard.DashboardActivity
import shota.suzuki.android_firebase_develop_app.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth
        setup()
    }

    private fun setup() {
        textview_register_next.setOnClickListener {
            startActivity( Intent(this, RegisterActivity::class.java) )
        }

        button_login.setOnClickListener {
            if (inputCheck()) {
                val email = edittext_email_login.text.toString()
                val password = edittext_password_login.text.toString()

                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (!it.isSuccessful) return@addOnCompleteListener

                        startActivity( Intent(this, DashboardActivity::class.java) )
                    }

            } else {
                Toast.makeText(this, "メールアドレス または パスワード が未入力です", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun inputCheck() : Boolean {
        return (edittext_email_login.text.isNotEmpty() && edittext_password_login.text.isNotEmpty())
    }
}
