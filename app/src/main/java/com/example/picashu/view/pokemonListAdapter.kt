package com.example.picashu.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.picashu.R
import com.example.picashu.model.ResultsItem

class PokemonListAdapter : RecyclerView.Adapter<PokemonListAdapter.PokeListViewHolder>() {

    private var mData: List<ResultsItem> = mutableListOf()

    fun setResults(data: List<ResultsItem>) {
        mData = data
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PokeListViewHolder {
        return PokeListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.pokemon_list_item, parent, false)
        )
    }


    override fun onBindViewHolder(holder: PokemonListAdapter.PokeListViewHolder, position: Int) {
        val pokeItem = mData.get(position)

        if (pokeItem.name != null){
            holder.nameItem.text = pokeItem.name
            Glide.with(holder.itemView).load("https://pokeres.bastionbot.org/images/pokemon/$position.png").into(holder.imageItem)
        }

    }


    override fun getItemCount(): Int {
        return if (mData.isNotEmpty()) mData.size else 0
    }

    class PokeListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imageItem = view.findViewById<ImageView>(R.id.image_pokemon)
        val nameItem = view.findViewById<TextView>(R.id.name_pokemon)
    }
}