package com.example.picashu.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.picashu.R
import com.example.picashu.model.Avis
import com.example.picashu.model.DataItem
import com.example.picashu.model.TradeCard

class AvisTradeAdapter (): RecyclerView.Adapter<AvisTradeViewHolder>() {

    private var mData: ArrayList<Avis> = ArrayList()

    fun setResults(data: ArrayList<Avis>) {
        mData = data
        Log.d("AvisTradeAdapter", "avisList:$mData")
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ):AvisTradeViewHolder{
        return AvisTradeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.avis_trade_item, parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: AvisTradeViewHolder,
        position: Int
    ) {
        val avisItem = mData[position]
        Log.d("AvisTradeAdapter", "avisListItem:$avisItem")

        holder.Bind(avisItem)

    }

    override fun getItemCount(): Int {
        return if (mData.isNotEmpty()) mData.size else 0
    }

}

class AvisTradeViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val userImage = view.findViewById<ImageView>(R.id.user_profilImg_trade)
    val tradeUsername = view.findViewById<TextView>(R.id.username_trade_user)
    val nbTrade = view.findViewById<TextView>(R.id.avisNb_trade_user)
    val tradeDate = view.findViewById<TextView>(R.id.date_trade_user)
    val commentTrade = view.findViewById<TextView>(R.id.comment_trade_user)

    fun Bind( result : Avis){

        Glide.with(itemView).load(result.fromImgUser).apply(RequestOptions.circleCropTransform()).into(userImage)
        tradeUsername.text = result.fromUsername
        nbTrade.text = "83 échanges"
        tradeDate.text = "21/01/12"
        commentTrade.text = result.avis

    }
}