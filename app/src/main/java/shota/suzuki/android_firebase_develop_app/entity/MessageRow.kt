package shota.suzuki.android_firebase_develop_app.entity

import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.messages_row.view.*
import shota.suzuki.android_firebase_develop_app.R

class MessageRow(val message: String): Item<ViewHolder>() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.textView_message.text = message
    }

    override fun getLayout(): Int {
        return R.layout.messages_row
    }
}