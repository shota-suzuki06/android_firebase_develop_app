package shota.suzuki.android_firebase_develop_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_write.*

class WriteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        btnSendId.setOnClickListener {
            writeDB()
        }
    }

    private fun writeDB() {
        val database = Firebase.database
        val myRef = database.getReference("message")

        val message = editMessageId.text.toString()
        myRef.setValue(message)
    }
}
