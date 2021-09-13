package com.example.picashu.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.picashu.R
import com.example.picashu.model.DataItem

class pokemonCardAdapter(private val listUser: List<DataItem>, private val listener: ItemClickListener) : RecyclerView.Adapter<pokemonCardAdapter.PokeCardListViewHolder>(){

    private var mData: ArrayList<DataItem> = ArrayList()

    fun setResults(data: ArrayList<DataItem>) {
        mData = data
        Log.d("pokemonADAPTER", "POKELISTDATA:${mData.size}")
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PokeCardListViewHolder {
        return PokeCardListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PokeCardListViewHolder, position: Int) {
        val pokeItem = mData.get(position)

        holder.bind(pokeItem,listener)
    }

    override fun getItemCount(): Int {
        return if (mData.isNotEmpty()) mData.size else 0    }

    interface ItemClickListener {
        fun onItemClickListener(poke: DataItem)
    }

    class PokeCardListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imageItem = view.findViewById<ImageView>(R.id.pokemon_card_main_pic)

        fun bind(result : DataItem, clickListener: ItemClickListener){

            if (result.name != null){
                Glide.with(itemView).load(result.images.small).into(imageItem)
            }

            itemView.setOnClickListener {
                clickListener.onItemClickListener(result)
            }
        }


    }
}
