
package com.example.picashu.view.fragment

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.picashu.R
import com.example.picashu.databinding.DetailCardFragmentBinding
import com.example.picashu.model.Card
import com.example.picashu.model.CardResponse.ResponseCard
import com.example.picashu.model.DataItem
import com.example.picashu.model.ResultsItem
import com.example.picashu.model.TradeCard
import com.example.picashu.view.TradeCardAdapter
import com.example.picashu.viewModel.PokemonApiViewModel
import kotlinx.android.synthetic.main.detail_card_fragment.*

class PokemonCardDetailFragment : Fragment(R.layout.detail_card_fragment),
    TradeCardAdapter.ItemClickListener {

    private lateinit var binding : DetailCardFragmentBinding
    private lateinit var mViewModel: PokemonApiViewModel
    private lateinit var mainImageView: ImageView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter : TradeCardAdapter
    private lateinit var currentCard : Card
    private lateinit var currentCardApiResponse : ResponseCard

    private lateinit var commentPopUp : Dialog


    private val USER_ID = "USER_ID"
    private val POKE_CARD ="POKE_CARD"
 
    private var listTradeOffer = ArrayList<TradeCard>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DetailCardFragmentBinding.inflate(inflater, container, false)
        mainImageView = binding.pokemonCardMainPic
        recyclerView = binding.recyclerViewDataTrade
        mViewModel = ViewModelProvider(this).get(PokemonApiViewModel::class.java)

        currentCard = requireArguments().getParcelable<Card>("POKE_CARD")!!
        val currentCardId = requireArguments().getString("POKE_ID")
        Log.d("pokemonSelectedCardID", "cardId : $currentCardId ")
        mViewModel.getSavedCardTradeOffer(currentCardId!!)
        mViewModel.getPokemonCardWithID(currentCardId!!)


        Glide.with(this).load(currentCard.image).into(mainImageView)

        retrieveListCardTrade()
        retrieveSelectedCardData()
        configureRecyclerView()
        return binding.root

    }

    private fun configureRecyclerView(){
        adapter = TradeCardAdapter(listTradeOffer,this)
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        adapter.setResults(listTradeOffer)
    }

    private fun retrieveSelectedCardData(){
        mViewModel.cardIdResponse.observe(viewLifecycleOwner,{
            currentCardApiResponse = it

            average_price_cm.text =
                " Average Price : ${currentCardApiResponse.data?.cardmarket?.prices?.averageSellPrice}€"
            lowest_price_cm.text =
                "Lowest Price : ${currentCardApiResponse.data?.cardmarket?.prices?.lowPrice}€"

            average_price_tgc.text =
                " Average Price : / "
            lowest_price_tgc.text =
                " Lowest Price : / "

        })
    }
    private fun retrieveListCardTrade(){
      mViewModel.savedTradeCardOffer.observe(viewLifecycleOwner,{
          listTradeOffer.addAll(it)
          adapter.notifyDataSetChanged()
      })
    }

    private fun showCommentPopUp(tradeCard: TradeCard){
        commentPopUp = Dialog(requireContext())
        commentPopUp.setContentView(R.layout.trade_comment_pop_uo)
        val comment = commentPopUp.findViewById<TextView>(R.id.comment_text_pop_up)
        comment.text =  tradeCard.cardComment
        commentPopUp.show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


            lien_cardmarket.setOnClickListener {

                val data = currentCardApiResponse.data?.cardmarket?.url
                if (data != null) {
                    val defaultBrowser =
                        Intent.makeMainSelectorActivity(
                            Intent.ACTION_MAIN,
                            Intent.CATEGORY_APP_BROWSER
                        )
                    defaultBrowser.data = Uri.parse(data)
                    startActivity(defaultBrowser)
                }

            }



            lien_tgcplayer.setOnClickListener {

                val data = currentCardApiResponse.data?.tcgplayer?.url
                if (data != null) {
                    val defaultBrowser =
                        Intent.makeMainSelectorActivity(
                            Intent.ACTION_MAIN,
                            Intent.CATEGORY_APP_BROWSER
                        )
                    defaultBrowser.data = Uri.parse(data)
                    startActivity(defaultBrowser)
                }

            }
        }




    override fun onItemClickListener(poke: TradeCard) {
        val bundle = Bundle()
        bundle.putParcelable(USER_ID,poke)
        bundle.putParcelable(POKE_CARD,currentCard)
        val profilUserFragment = ProfilUserFragment()
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        profilUserFragment.arguments = bundle
        transaction.replace(R.id.main_fragment, profilUserFragment).commit()
    }

    override fun onCommentBtnClickListener(poke: TradeCard) {
      showCommentPopUp(poke)
    }


}