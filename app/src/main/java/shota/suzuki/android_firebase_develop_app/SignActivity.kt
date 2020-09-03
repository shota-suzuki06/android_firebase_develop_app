package shota.suzuki.android_firebase_develop_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_sign.*
import shota.suzuki.android_firebase_develop_app.entity.User

class SignActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)

        btnUserSaveId.setOnClickListener {
            writeDB()
        }
    }

    private fun writeDB() {
//        val user = User(editUserName.text.toString(), editEmail.text.toString(), editPassword.text.toString())
//        database.child("users").child(editEmail.text.toString()).setValue(user)
    }
}
