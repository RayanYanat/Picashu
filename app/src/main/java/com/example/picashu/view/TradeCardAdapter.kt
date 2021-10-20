package com.example.picashu.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.picashu.R
import com.example.picashu.model.ResultsItem
import com.example.picashu.model.TradeCard

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

        fun Bind( result : TradeCard,clickListener: ItemClickListener){

            tradeUsername.text = result.username
            stateOfCard.text = result.etatCard
            languageOfCard.text = result.cardLanguage
            verionCard.text = result.versionCard


            itemView.setOnClickListener {
                clickListener.onItemClickListener(result)
            }

        }
    }

    interface ItemClickListener {
        fun onItemClickListener(poke: TradeCard)
    }
}