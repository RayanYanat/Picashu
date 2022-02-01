package com.example.picashu.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.picashu.R
import com.example.picashu.Trade
import com.example.picashu.model.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.latest_message_row.view.*

class ConcludedTradeAdapter (private val listConcludedTrade: List<Trade>) : RecyclerView.Adapter<ConcludedTradeListViewHolder>() {

    private var mData: ArrayList<Trade> = ArrayList()

    fun setResults(data: ArrayList<Trade>) {
        mData = data
        Log.d("PokemonSetAdapter", "POKELISTDATA:${mData.size}")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConcludedTradeListViewHolder {
        return ConcludedTradeListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.concluded_trade_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ConcludedTradeListViewHolder, position: Int) {
        val pokeItem = mData.get(position)
        holder.bind(pokeItem)

    }

    override fun getItemCount(): Int {
        return if (mData.isNotEmpty()) mData.size else 0    }
}

class ConcludedTradeListViewHolder (view: View) : RecyclerView.ViewHolder(view){

    val fromUserImg = view.findViewById<ImageView>(R.id.imageViewfromUser)
    val toUserImg =  view.findViewById<ImageView>(R.id.imageViewToUser)
    val tradeCardImg = view.findViewById<ImageView>(R.id.concludedCardImg)
    val cardVersion = view.findViewById<TextView>(R.id.concludedCardVersion)
    val cardEtat = view.findViewById<TextView>(R.id.concludedCardEtat)
    val cardLangue = view.findViewById<TextView>(R.id.concludedCardLangue)
    val date = view.findViewById<TextView>(R.id.concludedTradeDate)

    fun bind ( concludedTradeItem : Trade ){

        val ref = FirebaseFirestore.getInstance().collection("/users").document(concludedTradeItem.toId)
        val fromRef = FirebaseFirestore.getInstance().collection("/users").document(concludedTradeItem.fromId)

        ref.addSnapshotListener{snapshots, e ->
            if (e != null) {
                Log.w("TAG", "listen:error", e)
                return@addSnapshotListener
            }

            if (snapshots!!.exists()){
                val chatPartnerUser = snapshots.toObject(User::class.java)
                Glide.with(itemView).load(chatPartnerUser?.profileImageUrl).into(toUserImg)

            }
        }

        fromRef.addSnapshotListener{snapshots, e ->
            if (e != null) {
                Log.w("TAG", "listen:error", e)
                return@addSnapshotListener
            }

            if (snapshots!!.exists()){
                val chatPartnerUser = snapshots.toObject(User::class.java)
                Glide.with(itemView).load(chatPartnerUser?.profileImageUrl).into(fromUserImg)

            }
        }

        Glide.with(itemView).load(concludedTradeItem.cardImg).into(tradeCardImg)
        cardVersion.text = concludedTradeItem.versionCard
        cardEtat.text = concludedTradeItem.etatCard
        cardLangue.text = concludedTradeItem.cardLanguage
        date.text = concludedTradeItem.date

    }
}