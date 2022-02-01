package com.example.picashu.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.picashu.R
import com.example.picashu.model.Card
import com.example.picashu.model.DataItem

class PokemonCardAdapter(private val listUser: List<DataItem>, private val listener: ItemClickListener, private val listCard : List<Card>) : RecyclerView.Adapter<PokemonCardAdapter.PokeCardListViewHolder>(){

    private var mData: ArrayList<DataItem> = ArrayList()
    private var mResult : List<Card> = ArrayList()


    fun setResults(data: ArrayList<DataItem>) {
        mData = data
    }

    fun setCardResults ( result : List<Card> ){
        mResult = result
        Log.d("pokemonADAPTER", "POKELISTCardDATA:$listCard")
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
        val pokeItem = mData[position]

        holder.addButton.tag = position
        holder.deleteButton.tag = position




        holder.bind(pokeItem,listener,position)


    }

    override fun getItemCount(): Int {
        return if (mData.isNotEmpty()) mData.size else 0    }

    interface ItemClickListener {
        fun onItemClickListener(poke: DataItem)
        fun AddBtnClickListener(poke: DataItem, position: Int)
        fun deleteBtnClickListener(poke: DataItem, position: Int)
        fun tradeBtnClickListener(poke: DataItem, position: Int)
    }



    class PokeCardListViewHolder(view: View) : RecyclerView.ViewHolder(view) {




        val imageItem = view.findViewById<ImageView>(R.id.pokemon_card_main_pic)
        val addButton = itemView.findViewById<ImageButton>(R.id.addButton)
        val deleteButton = itemView.findViewById<ImageButton>(R.id.deleteButton)
        val tradeButton = itemView.findViewById<ImageButton>(R.id.tradeButton)




        fun bind(result : DataItem, clickListener: ItemClickListener, position: Int){




            if (result.deleteBtnVisible == true){
                deleteButton.visibility = View.VISIBLE
            }else{
                deleteButton.visibility = View.INVISIBLE
            }

            if (result.addBtnVisible == true){
                addButton.visibility = View.VISIBLE
            }else{
                addButton.visibility = View.INVISIBLE
            }

            if (result.tradeBtnVisible == true){
                tradeButton.visibility = View.VISIBLE
            }else{
                tradeButton.visibility = View.INVISIBLE
            }

            if (result.name != null){
                Glide.with(itemView).load(result.images.small).into(imageItem)
            }

            itemView.setOnClickListener {
                clickListener.onItemClickListener(result)
            }

                addButton.setOnClickListener{
                    if (it.tag == position){
                        clickListener.AddBtnClickListener(result,position)
                    }

                }

                deleteButton.setOnClickListener {
                    if (it.tag == position){
                        clickListener.deleteBtnClickListener(result,position)
                    }
                    }

                tradeButton.setOnClickListener {
                        clickListener.tradeBtnClickListener(result,position)

                }
            }
        }



    }

