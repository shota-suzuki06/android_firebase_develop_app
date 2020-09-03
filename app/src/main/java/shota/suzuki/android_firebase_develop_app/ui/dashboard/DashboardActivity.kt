package shota.suzuki.android_firebase_develop_app.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_dashboard.*
import shota.suzuki.android_firebase_develop_app.R
import shota.suzuki.android_firebase_develop_app.entity.MessageRow
import shota.suzuki.android_firebase_develop_app.ui.authentication.AuthenticationActivity
import shota.suzuki.android_firebase_develop_app.ui.storage.StorageActivity

class DashboardActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    val adapter = GroupAdapter<ViewHolder>()
    val messageList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Firebase Realtime Databaseを参照
        database = Firebase.database.reference

        dashborad_recycler_view.adapter = adapter
        dashborad_recycler_view.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        listenForMessages()

        setup()
    }

    private fun setup() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.selectedItemId = R.id.dashboard
        bottomNav.setOnNavigationItemSelectedListener {
            println(it.toString())
            println(R.id.storage.toString())
            when (it.itemId) {
                R.id.storage -> {
                    startActivity( Intent(this, StorageActivity::class.java) )
                    finish()
                }
                R.id.authentication -> {
                    startActivity( Intent(this, AuthenticationActivity::class.java) )
                    finish()
                }
            }
            true
        }

        button_send_dashboard.setOnClickListener {
            val message = editText_input_dashboard.text.toString()
            if (message.isEmpty()) {
                Toast.makeText(this, "メッセージを入力してください", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            writeDB(message)
            writeDBs(message)

            editText_input_dashboard.text.clear()
        }

        fetchMessages()
    }

    // addListenerForSingleValueEvent() は1回だけ呼び出す
    private fun fetchMessages() {
        database.child("/messages")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.children.forEach {
                            println(it.toString())
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })
    }

    // {key : value} 形式でデータを格納する
    private fun writeDB(message: String) {
        /**
         * child("...")     ⇒ データを格納する場所を参照
         * setValue("....") ⇒ 引数に渡したデータをデータベースに格納する
         **/
        database.child("/message")
                .setValue(message)
                .addOnSuccessListener {
                    Toast.makeText(this, "データを格納しました", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "データの格納に失敗しました", Toast.LENGTH_SHORT).show()
                }
    }

    // {key : {key :value}} 形式でデータを格納する
    private fun writeDBs(message: String) {
        // push() ⇒ messages配下の最後尾にデータを追加するためのメソッド
        database.child("/messages")
                .push()
                .setValue(message)
    }

    private fun listenForMessages() {
        val ref = Firebase.database.getReference("/messages")

        ref.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val message = snapshot.getValue() ?: return
                messageList.add(message.toString())
                refreshList()
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val message = snapshot.getValue() ?: return
                messageList.add(message.toString())
                refreshList()
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) { }
            override fun onChildRemoved(snapshot: DataSnapshot) { }
            override fun onCancelled(error: DatabaseError) { }
        })
    }

    private fun refreshList() {
        adapter.clear()
        messageList.forEach {
            adapter.add(MessageRow(it))
        }
    }

}

