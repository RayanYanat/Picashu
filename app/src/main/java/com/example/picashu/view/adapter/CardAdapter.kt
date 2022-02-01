package com.example.picashu.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.picashu.R
import com.example.picashu.model.Card


class CardAdapter (private val listener: ItemClickListener, private val listCard : List<Card>) : RecyclerView.Adapter<CardAdapter.PokeCardViewHolder>() {

    private var mData: ArrayList<Card> = ArrayList()

    fun setResults(data: ArrayList<Card>) {
        Log.d("CardAdapter", "cardList: $data")
        mData = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeCardViewHolder {
        return PokeCardViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.simple_card_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PokeCardViewHolder, position: Int) {
        val cardItem = mData[position]
        holder.bind(cardItem,listener)
    }

    override fun getItemCount(): Int {
        return if (mData.isNotEmpty()) mData.size else 0
    }

    class PokeCardViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val cardMainPic = view.findViewById<ImageView>(R.id.card_main_pic)

        fun bind (result : Card, clickListener: ItemClickListener){


                Glide.with(itemView).load(result.image).into(cardMainPic)

                itemView.setOnClickListener {
                    clickListener.onItemClickListener(result)
                }

            }
        }
    }

    interface ItemClickListener {
        fun onItemClickListener(poke: Card)
    }
