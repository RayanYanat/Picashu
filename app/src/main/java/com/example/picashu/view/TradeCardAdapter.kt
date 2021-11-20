package com.example.picashu.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.picashu.R
import com.example.picashu.model.ResultsItem
import com.example.picashu.model.TradeCard
import com.google.firebase.firestore.FirebaseFirestore

class TradeCardAdapter(private val listTradeOffer: List<TradeCard>, private val listener: ItemClickListener) : RecyclerView.Adapter<TradeCardAdapter.PokeTradeCardListViewHolder>() {

    private var mData: ArrayList<TradeCard> = ArrayList()

    fun setResults(data: ArrayList<TradeCard>) {
        mData = data
        Log.d("pokemonADAPTER", "POKELISTDATA:${mData.size}")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeTradeCardListViewHolder {
        return PokeTradeCardListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_trade_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PokeTradeCardListViewHolder, position: Int) {
        val tradeOfferItem = mData[position]

        holder.Bind(tradeOfferItem,listener)
    }

    override fun getItemCount(): Int {
        return if (mData.isNotEmpty()) mData.size else 0
    }


    class PokeTradeCardListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val userImage = view.findViewById<ImageView>(R.id.user_profilImg_trade)
        val tradeUsername = view.findViewById<TextView>(R.id.username_trade_user)
        val stateOfCard  = view.findViewById<TextView>(R.id.trade_card_state1)
        val languageOfCard  = view.findViewById<TextView>(R.id.trade_card_langue1)
        val verionCard  = view.findViewById<TextView>(R.id.trade_card_version1)
        val commentBtn = view.findViewById<ImageButton>(R.id.comment_trade_btn)
        val nbAvisUser = view.findViewById<TextView>(R.id.nb_user_avis)

        fun Bind( result : TradeCard ,clickListener: ItemClickListener){

            val ref = FirebaseFirestore.getInstance()
                .collection("/users/${result.userId}/avis")


            ref.addSnapshotListener { snapshots, e ->

                if (e != null) {
                    Log.w("TAG", "listen:error", e)
                    return@addSnapshotListener
                }
                    nbAvisUser.text = "${snapshots?.size()} Avis"


            }

            if (result.cardComment == ""){
                commentBtn.visibility = View.INVISIBLE
            }

            Log.d("TradeCardAdapter", "userProfilImg :${result.profilImg}")

            tradeUsername.text = result.username
            stateOfCard.text = result.etatCard
            languageOfCard.text = result.cardLanguage
            verionCard.text = result.versionCard

            if (result.profilImg != null){
                Glide.with(itemView).load(result.profilImg).apply(RequestOptions.circleCropTransform()).into(userImage)
            }

            itemView.setOnClickListener {
                clickListener.onItemClickListener(result)
            }

            commentBtn.setOnClickListener {
                clickListener.onCommentBtnClickListener(result)
            }

        }
    }

    interface ItemClickListener {
        fun onItemClickListener(poke: TradeCard)
        fun onCommentBtnClickListener (poke : TradeCard)
    }
}