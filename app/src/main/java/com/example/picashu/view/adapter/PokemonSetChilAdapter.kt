package com.example.picashu.view.adapter

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
import com.example.picashu.model.Card
import com.example.picashu.model.CardSetResponse.DataItem

class PokemonSetChilAdapter(private val listUser: List<DataItem>, private val listener: ItemClickListener, private val listUserCard : List<Card>) : RecyclerView.Adapter<PokemonSetChilAdapter.PokeSetChildListViewHolder>() {

    private var mData: ArrayList<DataItem> = ArrayList()

    fun setResults(data: ArrayList<DataItem>) {
        mData = data
        Log.d("PokemonSetAdapter", "POKELISTDATA:${mData.size}")
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PokeSetChildListViewHolder {
        return PokeSetChildListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.child_set_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PokeSetChildListViewHolder, position: Int) {
        val pokeItem = mData.get(position)

        holder.bind(pokeItem,listener,listUserCard)
    }

    override fun getItemCount(): Int {
        return if (mData.isNotEmpty()) mData.size else 0
    }

    class PokeSetChildListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imageItem = view.findViewById<ImageView>(R.id.pokemon_set_child_pic)
        val nameItem = view.findViewById<TextView>(R.id.pokemon_set_child_name)
        val ratioItem = view.findViewById<TextView>(R.id.pokemon_set_child_ratio)

        @SuppressLint("SetTextI18n")
        fun bind(result : DataItem, clickListener: ItemClickListener, listUserCard: List<Card>){

            var setTotalCardOwned = 0

            listUserCard.forEach {
                if (it.set == result.name){
                    setTotalCardOwned++
                }
            }

            if (result.name != null){

                nameItem.text = result.name
                Glide.with(itemView).load(result.images?.logo).into(imageItem)
                ratioItem.text = "$setTotalCardOwned/${result.total}"
            }



            itemView.setOnClickListener {
                clickListener.onItemClickListener(result)
            }

        }


    }

    interface ItemClickListener {
        fun onItemClickListener(poke: DataItem)
    }

}