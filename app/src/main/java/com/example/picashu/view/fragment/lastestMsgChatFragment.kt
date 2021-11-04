package com.example.picashu.view.fragment

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.picashu.R
import com.example.picashu.model.Avis
import com.example.picashu.model.ChatMessage
import com.example.picashu.model.User
import com.example.picashu.view.ChatFromItem
import com.example.picashu.view.ChatToItem
import com.example.picashu.view.LatestMessageRow
import com.example.picashu.view.activity.ChatLogActivity
import com.example.picashu.view.fragment.ProfilUserFragment.Companion.FROM_USER_KEY
import com.example.picashu.view.fragment.ProfilUserFragment.Companion.TO_USER_KEY
import com.example.picashu.viewModel.FirebaseViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder

class lastestMsgChatFragment: Fragment(), LatestMessageRow.ItemClickListener {

    private val adapter = GroupAdapter<ViewHolder>()
    private lateinit var mViewModel: FirebaseViewModel
    private val latestMessagesMap = HashMap<String, ChatMessage>()
    private var fromUser : User? = null
    private lateinit var tradePopUp : Dialog

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

    private fun refreshRecyclerViewMessages() {
        adapter.clear()
        latestMessagesMap.values.forEach {
            Log.d("lastestChatLog3",it.text)
            adapter.add(LatestMessageRow(it,this))
        }
    }

    private fun ShowPopup( chatMessage: ChatMessage){

        tradePopUp = Dialog(requireContext())
        tradePopUp.setContentView(R.layout.custom_pop_up)

        val fromImg = tradePopUp.findViewById<ImageView>(R.id.user_profilImg_from)
        val toImg = tradePopUp.findViewById<ImageView>(R.id.user_profilImg_to)
        val exitBtn = tradePopUp.findViewById<ImageButton>(R.id.imageButtonExit)

        val validateBtn = tradePopUp.findViewById<Button>(R.id.button2)

        val avis = tradePopUp.findViewById<EditText>(R.id.trade_card_avis)
        val livraisonRatingBar = tradePopUp.findViewById<RatingBar>(R.id.ratingBar)
        val communicationRatingBar = tradePopUp.findViewById<RatingBar>(R.id.ratingbar2)

        //retrieve image pp users
        Glide.with(this).load(fromUser!!.profileImageUrl).into(fromImg)

        mViewModel.getUser(chatMessage.toId).observe(viewLifecycleOwner,{

            Glide.with(this).load(it.profileImageUrl).into(toImg)
        })

        exitBtn.setOnClickListener {
            tradePopUp.dismiss()
        }

        validateBtn.setOnClickListener {
            val avisCmt =  avis.text.toString()
            val livraisonRating = livraisonRatingBar.rating
            val communicationRating = communicationRatingBar.rating
            val avis = Avis(communicationRating,livraisonRating,avisCmt,chatMessage.fromId,fromUser!!.profileImageUrl,fromUser!!.username)

            mViewModel.createAvis(avis,chatMessage.toId)
            tradePopUp.dismiss()

        }
        tradePopUp.show()
    }

    private fun listenForLatestMessages(){
        val fromId = FirebaseAuth.getInstance().uid


                val ref = FirebaseFirestore.getInstance().collection("/latest-messages/$fromId/contact")
                ref.addSnapshotListener {snapshots, e ->
                    if (e != null) {
                        Log.w("TAG", "listen:error", e)
                        return@addSnapshotListener
                    }

                    for (dc in snapshots!!.documentChanges) {
                        when (dc.type) {
                            DocumentChange.Type.ADDED -> {
                                val chatMessage = dc.document.toObject(ChatMessage::class.java)
                                latestMessagesMap[dc.document.id] = chatMessage
                                refreshRecyclerViewMessages()
                            }
                            DocumentChange.Type.MODIFIED -> {
                                val chatMessage = dc.document.toObject(ChatMessage::class.java)
                                latestMessagesMap[dc.document.id] = chatMessage
                                refreshRecyclerViewMessages()
                            }
                            DocumentChange.Type.REMOVED -> { }
                        }
                    }
                }
            }

    override fun onItemClickListenerValidate(poke: ChatMessage) {
        Log.d("lastestMsgChatFragment", "currentChatMessage:${poke.fromId} ")
        ShowPopup(poke)
    }

    override fun onItemClickListenerDelete(poke: ChatMessage) {
        Log.d("lastestMsgChatFragment", "currentChatMessage:${poke.toId} ")

    }
}









