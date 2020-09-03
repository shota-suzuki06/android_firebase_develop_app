package shota.suzuki.android_firebase_develop_app.ui.storage

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_storage.*
import kotlinx.android.synthetic.main.activity_storage.button_clear_storage
import shota.suzuki.android_firebase_develop_app.R
import shota.suzuki.android_firebase_develop_app.ui.authentication.AuthenticationActivity
import shota.suzuki.android_firebase_develop_app.ui.dashboard.DashboardActivity
import java.io.File
import java.util.*

class StorageActivity : AppCompatActivity() {

    private lateinit var storage: FirebaseStorage
    var selectedPhotoUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storage)
        title = "Storage"
        storage = Firebase.storage

        setup()
    }

    private fun setup() {
        button_select_photo.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }

        button_clear_storage.setOnClickListener {

        }

        button_send_storage.setOnClickListener {
            uploadImageToFirebaseStorage()
        }

        button_download.setOnClickListener {
            fetchImageToFirebaseStorage()
        }

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.selectedItemId = R.id.storage
        bottomNav.setOnNavigationItemSelectedListener {
            println(it.toString())
            println(R.id.storage.toString())
            when (it.itemId) {
                R.id.dashboard -> {
                    startActivity( Intent(this, DashboardActivity::class.java) )
                    finish()
                }
                R.id.authentication -> {
                    startActivity( Intent(this, AuthenticationActivity::class.java) )
                    finish()
                }
            }
            true
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            selectedPhotoUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)
            imageView_select_photo.setImageBitmap(bitmap)
            button_select_photo.alpha = 0f
        }
    }

    private fun uploadImageToFirebaseStorage() {
        val fileName = UUID.randomUUID().toString()
        val storageRef = storage.reference.child("/images/$fileName")
        storageRef.putFile(selectedPhotoUri!!)
                  .addOnSuccessListener {
                      Log.d("storage", "success upload image: ${it.metadata?.path}")

                      storageRef.downloadUrl.addOnSuccessListener {
                          Log.d("storage", "File Location: $it")
                      }
                  }
                  .addOnFailureListener {

                  }
    }

    private fun fetchImageToFirebaseStorage() {

        val storageRef = storage.reference.child("/images/4162ab84-7773-4e53-beb9-691d77364fd0")
        val localFile = File.createTempFile("images", "jpg")
        storageRef.getFile(localFile)
                  .addOnSuccessListener {
                      println("成功！")
                  }
                  .addOnFailureListener {
                      println("失敗！")
                  }


    }

}
