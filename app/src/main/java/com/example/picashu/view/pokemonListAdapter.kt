package com.example.picashu.view

import android.util.Log
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
import com.example.picashu.model.Pokemon
import com.example.picashu.model.ResultsItem

class PokemonListAdapter : RecyclerView.Adapter<PokemonListAdapter.PokeListViewHolder>() {

    private var mData: ArrayList<ResultsItem> = ArrayList()

    fun setResults(data: ArrayList<ResultsItem>) {
        mData = data
        Log.d("pokemonADAPTER", "POKELISTDATA:" + mData.size)
        notifyDataSetChanged()
    }
    fun getData(): ArrayList<ResultsItem> {
        return mData
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

        val url = pokeItem.url
        Log.d("pokemonADAPTER", "urlParse:$url")
        val pokeNumber = url?.split("/".toRegex())?.dropLast(1)?.last()

        if (pokeItem.name != null){
            holder.nameItem.text = pokeItem.name
            Glide.with(holder.itemView).load("https://pokeres.bastionbot.org/images/pokemon/${pokeNumber}.png").into(holder.imageItem)
        }

    }

    fun addPokemonToList( pokemonList : ArrayList<ResultsItem>){
        mData.addAll(pokemonList)
        Log.d("pokemonADAPTER", "fullPOKElIST:" + mData.size)

        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        Log.d("pokemonADAPTER", "fullPOKElISTcount:" + mData.size)
        return if (mData.isNotEmpty()) mData.size else 0
    }

    class PokeListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imageItem = view.findViewById<ImageView>(R.id.image_pokemon)
        val nameItem = view.findViewById<TextView>(R.id.name_pokemon)
    }
}

