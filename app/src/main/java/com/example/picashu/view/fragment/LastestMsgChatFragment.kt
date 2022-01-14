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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.picashu.R
import com.example.picashu.Trade
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
import com.example.picashu.viewModel.PokemonApiViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.custom_pop_up.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import kotlin.collections.HashMap

class LastestMsgChatFragment: Fragment(), LatestMessageRow.ItemClickListener {

    private val adapter = GroupAdapter<ViewHolder>()
    private lateinit var mViewModel: PokemonApiViewModel
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
        mViewModel = ViewModelProvider(this).get(PokemonApiViewModel::class.java)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))

        val currentUserId = FirebaseAuth.getInstance().uid
        mViewModel.getUser(currentUserId!!).observe(viewLifecycleOwner, { user ->
            fromUser = user
            Log.d("DetailAnnonceFragSid", "DetailAnnonceFragment:${fromUser}")
        })


        val lastConnexionUser = FirebaseAuth.getInstance().currentUser?.metadata?.lastSignInTimestamp
        val creationUser = FirebaseAuth.getInstance().currentUser?.metadata?.creationTimestamp

        Log.d("LastMsgFrag"," lastSignInTimeStamp = ${getDateTime(lastConnexionUser.toString())}")
        Log.d("LastMsgFrag"," creationTimeStamp = ${getDateTime(creationUser.toString())}")

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

    private fun getDateTime(s: String): String? {
        try {
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            val netDate = Date(s.toLong() )
            return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
    }

    private fun refreshRecyclerViewMessages() {
        adapter.clear()
        latestMessagesMap.values.forEach {
            Log.d("lastestChatLog3",it.text)
            adapter.add(LatestMessageRow(it,this))
        }
    }

    private fun ShowPopup( chatMessage: ChatMessage,position: Int){

        tradePopUp = Dialog(requireContext())
        tradePopUp.setContentView(R.layout.custom_pop_up)
        val cardImage = tradePopUp.findViewById<ImageView>(R.id.cardImgPopUp)
        val fromImg = tradePopUp.findViewById<ImageView>(R.id.user_profilImg_from)
        val toImg = tradePopUp.findViewById<ImageView>(R.id.user_profilImg_to)
        val exitBtn = tradePopUp.findViewById<ImageButton>(R.id.imageButtonExit)
        var currentTradeCard : Trade? = null

        mViewModel.getCurrentTradeCard(chatMessage.fromId,chatMessage.toId).observe(viewLifecycleOwner, {
            currentTradeCard = it
            Glide.with(this).load(it.cardImg).into(cardImage)
        })


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

            val calendar = Calendar.getInstance()
            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
            val date = dateFormat.format(calendar.time)

            val avisCmt =  avis.text.toString()
            val livraisonRating = livraisonRatingBar.rating
            val communicationRating = communicationRatingBar.rating
            val avis = Avis(communicationRating,livraisonRating,avisCmt,chatMessage.fromId,fromUser!!.profileImageUrl,fromUser!!.username,date)

            adapter.removeGroup(position)
            adapter.notifyItemRemoved(position)

            val latestMessageRef =
                FirebaseFirestore.getInstance().collection("/latest-messages/${chatMessage.fromId}/contact")
            latestMessageRef.document(chatMessage.toId).delete()


            mViewModel.createAvis(avis,chatMessage.toId)
            mViewModel.addConcludedTrade(chatMessage.fromId,currentTradeCard!!)
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
                            DocumentChange.Type.REMOVED -> {

                            }
                        }
                    }
                }
            }

    override fun onItemClickListenerValidate(poke: ChatMessage,position :Int) {
//        val trade = Trade(poke.fromId,poke.toId,)
        Log.d("lastestMsgChatFragment", "currentChatMessage:${poke.fromId} ")
        ShowPopup(poke,position)
      //  mViewModel.addConcludedTrade(poke.fromId,trade)
    }

    override fun onItemClickListenerDelete(poke: ChatMessage, position :Int) {
        Log.d("lastestMsgChatFragment", "currentChatMessage:${poke.toId} ")

    }
}









