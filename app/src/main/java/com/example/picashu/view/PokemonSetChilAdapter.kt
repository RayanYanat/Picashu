package com.example.picashu.view

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.picashu.R
import com.example.picashu.model.CardSetResponse.DataItem
import com.example.picashu.model.ResultsItem

class PokemonSetChilAdapter(private val listUser: List<DataItem>, private val listener: PokemonSetChilAdapter.ItemClickListener) : RecyclerView.Adapter<PokemonSetChilAdapter.PokeSetChildListViewHolder>() {

    private var mData: ArrayList<DataItem> = ArrayList()

    fun setResults(data: ArrayList<DataItem>) {
        mData = data
        Log.d("PokemonSetAdapter", "POKELISTDATA:${mData.size}")
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PokemonSetChilAdapter.PokeSetChildListViewHolder {
        return PokemonSetChilAdapter.PokeSetChildListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.child_set_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PokemonSetChilAdapter.PokeSetChildListViewHolder, position: Int) {
        val pokeItem = mData.get(position)

        holder.bind(pokeItem)
    }

    override fun getItemCount(): Int {
        return if (mData.isNotEmpty()) mData.size else 0
    }

    class PokeSetChildListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imageItem = view.findViewById<ImageView>(R.id.pokemon_set_child_pic)
        val nameItem = view.findViewById<TextView>(R.id.pokemon_set_child_name)
        val ratioItem = view.findViewById<TextView>(R.id.pokemon_set_child_ratio)

        @SuppressLint("SetTextI18n")
        fun bind(result : DataItem){

            if (result.name != null){

                nameItem.text = result.name
                Glide.with(itemView).load(result.images?.logo).into(imageItem)
                ratioItem.text = "0/${result.total}"
            }

        }


    }

    interface ItemClickListener {
        fun onItemClickListener(poke: ResultsItem)
    }

}