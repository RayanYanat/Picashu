package com.example.picashu.view.adapter

import android.util.Log
import com.bumptech.glide.Glide
import com.example.picashu.R
import com.example.picashu.model.User
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.chat_from_row.view.*
import kotlinx.android.synthetic.main.chat_to_row.view.*

class ChatFromItem(val text: String,val user: User): Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        val targetImageView = viewHolder.itemView.imageView
        viewHolder.itemView.textview_from_roww.text = text
        Glide.with(viewHolder.itemView).load(user.profileImageUrl).into(targetImageView)
        Log.d("ChatLogFromPic", user.profileImageUrl)

    }

    override fun getLayout(): Int {
        return R.layout.chat_from_row
    }
}

class ChatToItem(val text: String,val user: User): Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        val targetImageView = viewHolder.itemView.imageView_to_row
        viewHolder.itemView.textview_to_row.text = text
        Glide.with(viewHolder.itemView).load(user.profileImageUrl).into(targetImageView)
        Log.d("ChatLogToPic", user.profileImageUrl)

    }

    override fun getLayout(): Int {
        return R.layout.chat_to_row
    }



}