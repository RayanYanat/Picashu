package com.example.picashu.view.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.picashu.R
import com.example.picashu.model.ChatMessage
import com.example.picashu.model.User
import com.example.picashu.view.LatestMessageRow
import com.example.picashu.view.activity.ChatLogActivity
import com.example.picashu.view.fragment.ProfilUserFragment.Companion.FROM_USER_KEY
import com.example.picashu.view.fragment.ProfilUserFragment.Companion.TO_USER_KEY
import com.example.picashu.viewModel.FirebaseViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder

class lastestMsgChatFragment: Fragment() {

    private val adapter = GroupAdapter<ViewHolder>()
    private lateinit var mViewModel: FirebaseViewModel
    private val latestMessagesMap = HashMap<String, ChatMessage>()
    private var fromUser : User? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.lastes_chat_msg_fragment, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview_latest_messages)
        mViewModel = ViewModelProvider(this).get(FirebaseViewModel::class.java)

        recyclerView.adapter = adapter

        val currentUserId = FirebaseAuth.getInstance().uid
        mViewModel.getUser(currentUserId!!).observe(viewLifecycleOwner, { user ->
            fromUser = user
            Log.d("DetailAnnonceFragSid", "DetailAnnonceFragment:${fromUser}")
        })

        adapter.setOnItemClickListener { item, view ->
            val intent = Intent(context, ChatLogActivity::class.java)
            val row = item as LatestMessageRow
            intent.putExtra(TO_USER_KEY, row.chatPartnerUser)
            intent.putExtra(FROM_USER_KEY,fromUser)
            startActivity(intent)
        }

        listenForLatestMessages()



        return view
    }



    private fun listenForLatestMessages(){
        val fromId = FirebaseAuth.getInstance().uid

        mViewModel.getSavedUsers().observe(viewLifecycleOwner, {
            it.forEach { user ->

                Log.d("lastestChatLog", user.uid)

                val ref = FirebaseFirestore.getInstance().collection("/latest-messages/$fromId/${user.uid}").document("lastestMsg")
                ref.addSnapshotListener {snapshots, e ->
                    if (e != null) {
                        Log.w("TAG", "listen:error", e)
                        return@addSnapshotListener
                    }
                    if (snapshots!!.exists()){
                        val chatMessage = snapshots.toObject(ChatMessage::class.java)
                        Log.d("lastestChatLog", chatMessage!!.text)
                        latestMessagesMap[snapshots.id] = chatMessage
                        Log.d("lastestChatLog2", chatMessage.text)
                        refreshRecyclerViewMessages()
                    }
                }
            }
        })
    }

    private fun refreshRecyclerViewMessages() {
        adapter.clear()
        latestMessagesMap.values.forEach {
            Log.d("lastestChatLog3",it.text)
            adapter.add(LatestMessageRow(it))
        }
    }

}