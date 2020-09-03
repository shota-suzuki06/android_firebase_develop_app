package shota.suzuki.android_firebase_develop_app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import shota.suzuki.android_firebase_develop_app.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_auth)

        auth = Firebase.auth

        // Setup
        setup()
    }

    private fun setup() {
        title = "Authentication"

        binding.btnNewUserId.setOnClickListener {
            startActivity( Intent(this, SignActivity::class.java) )
        }

        binding.apply {
          btnRegisterId.setOnClickListener {
              if (editEmailId.text.isNotEmpty() && editPasswordId.text.isNotEmpty()) {
                  auth.createUserWithEmailAndPassword(editEmailId.text.toString(), editPasswordId.text.toString()).addOnCompleteListener {
                      if (it.isSuccessful) {
                          showHome(it.result?.user?.email ?: "", ProviderType.BASIC)
                      } else {
                          showAlert("ユーザ登録に失敗しました")
                      }
                  }

              } else {
                  showAlert("メールアドレス または パスワードを入力してください")
              }
          }
        }

        binding.apply {
            btnLoginId.setOnClickListener {
                if (editEmailId.text.isNotEmpty() && editPasswordId.text.isNotEmpty()) {
                    auth.signInWithEmailAndPassword(editEmailId.text.toString(), editPasswordId.text.toString()).addOnCompleteListener {
                        if (it.isSuccessful) {
                            showHome(it.result?.user?.email ?: "", ProviderType.BASIC)
                        } else {
                            showAlert("ログインに失敗しました")
                        }
                    }
                } else {
                    showAlert("メールアドレス または パスワードを入力してください")
                }
            }
        }

    }

    private fun showAlert(errorMessage: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage(errorMessage)
        builder.setPositiveButton("OK", null)
        builder.create().show()
    }

    private fun showHome(email: String, provider: ProviderType) {
        val homeIntent = Intent(this, HomeActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(homeIntent)
    }








}
