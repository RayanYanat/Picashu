package com.example.picashu.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.example.picashu.PokemonApiService
import com.example.picashu.model.CardResponse.ResponseCard
import com.example.picashu.model.CardSetResponse.CardSet
import com.example.picashu.model.DataItem
import com.example.picashu.model.PokemonListeResponse
import com.example.picashu.model.Response
import com.example.picashu.repository.PokemonApiRepository
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PokemonApiViewModel(app: Application)  : AndroidViewModel(app) {

    private var pokemonApiRepository = PokemonApiRepository(app)

    val response : MutableLiveData<PokemonListeResponse>
    val cardResponse : MutableLiveData<Response>
    val cardIdResponse : MutableLiveData<ResponseCard>
    val setResponse : MutableLiveData<CardSet>

    init {
        response = pokemonApiRepository.response
        cardResponse = pokemonApiRepository.cardResponse
        cardIdResponse = pokemonApiRepository.cardIdResponse
        setResponse = pokemonApiRepository.setResponse
    }

     fun getPokemonIds( limit : Int, offset : Int) {
         viewModelScope.launch {
             pokemonApiRepository.getPokemonID(limit, offset)
         }
    }

    fun getPokemonCards (name : String, apiKey : String){
        viewModelScope.launch {
            pokemonApiRepository.getPokemonCard(name, apiKey)
        }
    }

    fun getPokemonCardWithID (id : String){
        viewModelScope.launch {
            pokemonApiRepository.getPokemonCardWithId(id)
        }
    }

    fun getPokemonSets (id : String, apiKey : String){
        viewModelScope.launch {
            pokemonApiRepository.getPokemonSet(id,apiKey)
        }
    }

}