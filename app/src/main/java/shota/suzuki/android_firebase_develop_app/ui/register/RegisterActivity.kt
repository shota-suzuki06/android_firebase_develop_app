package shota.suzuki.android_firebase_develop_app.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_register.*
import shota.suzuki.android_firebase_develop_app.R
import shota.suzuki.android_firebase_develop_app.entity.User
import shota.suzuki.android_firebase_develop_app.ui.dashboard.DashboardActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = Firebase.auth

        setup()
    }

    private fun setup() {
        textview_login_back_register.setOnClickListener {
            finish()
        }

        button_clear_storage.setOnClickListener {
            edittext_username_register.text.clear()
            edittext_email_register.text.clear()
            edittext_password_register.text.clear()
            edittext_confirm_password_register.text.clear()
        }

        button_register.setOnClickListener {
            return@setOnClickListener
            if (inputCheck()) {
                registerUser()
            } else {
                println("登録できません")
            }
        }
    }

    private fun registerUser() {
        // まずはAuthenticationにユーザーを作成する
        val userName = edittext_username_register.text.toString()
        val email = edittext_email_register.text.toString()
        val password = edittext_password_register.text.toString()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener

                val uid = auth.uid ?: ""
                database = Firebase.database.getReference("/users/$uid")

                val user = User(uid, userName, email, password)
                database.setValue(user)
                        .addOnSuccessListener {
                            startActivity( Intent(this, DashboardActivity::class.java) )
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "ユーザー情報の登録に失敗しました : ${it.message}", Toast.LENGTH_SHORT).show()
                        }
            }
            .addOnFailureListener {
                Toast.makeText(this, "ユーザー作成に失敗しました : ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun inputCheck(): Boolean {
        return (edittext_username_register.text.isNotEmpty()
                && edittext_email_register.text.isNotEmpty()
                && edittext_password_register.text.isNotEmpty()
                && edittext_confirm_password_register.text.isNotEmpty()) &&
                (edittext_password_register.text.toString().equals( edittext_confirm_password_register.text.toString() ))
    }
}
