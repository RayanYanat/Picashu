package com.example.picashu.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.picashu.R
import com.example.picashu.databinding.CardFragmentListBinding
import com.example.picashu.model.Card
import com.example.picashu.model.DataItem
import com.example.picashu.view.adapter.PokemonCardAdapter
import com.example.picashu.viewModel.PokemonCardListFragmentViewModel
import com.google.firebase.auth.FirebaseAuth


class PokemonCardListFragment : Fragment(R.layout.card_fragment_list), PokemonCardAdapter. ItemClickListener {

    private lateinit var binding: CardFragmentListBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var mViewModel: PokemonCardListFragmentViewModel
    private lateinit var adapter: PokemonCardAdapter

    private val POKE_ID = "POKE_ID"
    private val POKE_CARD ="POKE_CARD"
    private val POKE_IMG_URL ="POKE_IMG_URL"

    private var listPokemonCardData = ArrayList<DataItem>()
    private var listUserCardData = ArrayList<Card>()
    private var listUserCardData1 = ArrayList<DataItem>()

    val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProvider(this).get(PokemonCardListFragmentViewModel::class.java)

        binding = CardFragmentListBinding.inflate(layoutInflater
        )
        val currentCardName = requireArguments().getString("POKE_NAME")
        val currentSetId = requireArguments().getString("POKE_SET")
        Log.d("updateUI", "currentCardName:$currentCardName")
        val currentCardNameFor = "name:$currentCardName"
        val currentSetIdFor = "set.id:$currentSetId"

        mViewModel.getSavedUserCards(currentUserId)

        if (currentCardName != null){
            mViewModel.getPokemonCards(currentCardNameFor,"")
        }else if (currentSetId != null){
            mViewModel.getPokemonCards(currentSetIdFor,"")
        }


        retrieveUserCard()
        pokemonCardListApiCall()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       // binding = CardFragmentListBinding.inflate(inflater, container, false)

        recyclerView = binding.recyclerViewCardData


//        val currentCardName = requireArguments().getString("POKE_NAME")
//        val currentSetId = requireArguments().getString("POKE_SET")
//        Log.d("updateUI", "currentCardName:$currentCardName")
//        val currentCardNameFor = "name:$currentCardName"
//        val currentSetIdFor = "set.id:$currentSetId"
//
//        mViewModel.getSavedUserCards(currentUserId)
//
//        if (currentCardName != null){
//            mViewModel.getPokemonCards(currentCardNameFor,"")
//        }else if (currentSetId != null){
//            mViewModel.getPokemonCards(currentSetIdFor,"")
//        }
//
//
//        retrieveUserCard()
//        pokemonCardListApiCall()
        configureRecyclerView()

        return binding.root
    }


    private fun configureRecyclerView(){

        adapter = PokemonCardAdapter(listPokemonCardData,this,listUserCardData)
        val layoutManager = GridLayoutManager(activity, 3)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        adapter.setResults(listPokemonCardData)
        adapter.setCardResults(listUserCardData)
    }

    private fun retrieveUserCard(){
        mViewModel.savedCard.observe(this,{
            listUserCardData.addAll(it)
            Log.d("retrieveUserCard", "currentCardList:$listUserCardData zt size ${listUserCardData.size}")
           // adapter.notifyDataSetChanged()
        })
    }

    private fun pokemonCardListApiCall() {

        mViewModel.cardResponse.observe(this, Observer { response ->
            val result = response.data
            listPokemonCardData.addAll(result)
            Log.d("PokemonCardListFragment", "listPokemonCardData = ${listUserCardData.size}")
            listPokemonCardData.forEach {

                    it.addBtnVisible = true
                    it.deleteBtnVisible = false
                    it.tradeBtnVisible = false

                    listUserCardData.forEach { card ->
                        if ( card.id == it.id ){
                            it.addBtnVisible = false
                            it.deleteBtnVisible = true
                            it.tradeBtnVisible = true
                            listUserCardData1.add(it)
                            Log.d("listUserCardData1", "id of card in collection:${listUserCardData1.size}")
                        }
                    }
                    Log.d("listUserCardData2", "id of card in collection:${listUserCardData1.size}")

                }
                adapter.notifyDataSetChanged()

        })

    }

    override fun onItemClickListener(poke: DataItem) {

        val bundle = Bundle()
        val card = Card(poke.id,poke.set.name,poke.name,poke.images.small,poke.set.series)
        val pokemonCardDetailFragment = PokemonCardDetailFragment()
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        bundle.putParcelable(POKE_CARD,card)
        bundle.putString(POKE_ID, poke.id)
        pokemonCardDetailFragment.arguments = bundle
        transaction.replace(R.id.main_fragment, pokemonCardDetailFragment).addToBackStack(null).commit()
        Log.d("currentBtnState", "currentBtnState: add = ${poke.addBtnVisible}; delete = ${poke.deleteBtnVisible}; trad = ${poke.tradeBtnVisible}")
    }

    override fun AddBtnClickListener(poke: DataItem,position :Int) {
        val currentUid = FirebaseAuth.getInstance().currentUser!!.uid
        mViewModel.CreateCard(Card(poke.id,poke.set.name,poke.name,poke.images.small,poke.set.series),currentUid)
        poke.addBtnVisible = false
        poke.deleteBtnVisible = true
        poke.tradeBtnVisible = true
        adapter.notifyItemChanged(position)
        Toast.makeText(context,"${poke.name} from the set ${poke.set.name} was added to your collection",Toast.LENGTH_SHORT).show()

    }

    override fun deleteBtnClickListener(poke: DataItem,position :Int) {
        val currentUid = FirebaseAuth.getInstance().currentUser!!.uid
       mViewModel.DeleteCard(Card(poke.id,poke.set.name,poke.name,poke.images.small,poke.set.series),currentUid)
        poke.addBtnVisible = true
        poke.deleteBtnVisible = false
        poke.tradeBtnVisible = false
        adapter.notifyItemChanged(position)
        Toast.makeText(context,"${poke.name} from the set ${poke.set.name} was deleted from your collection",Toast.LENGTH_SHORT).show()
    }

    override fun tradeBtnClickListener(poke: DataItem,position :Int) {
        val bundle = Bundle()
        val tradeCardFragment = TradeCardFragment()
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        bundle.putString(POKE_ID, poke.id)
        bundle.putString(POKE_IMG_URL,poke.images.small)
        tradeCardFragment.arguments = bundle
        transaction.replace(R.id.main_fragment, tradeCardFragment).commit()
        Toast.makeText(context,"${poke.name} from the set ${poke.set.name} was traded from your collection",Toast.LENGTH_SHORT).show()
    }

}