package com.example.picashu.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.picashu.PokemonApiService
import com.example.picashu.model.PokemonListeResponse
import com.example.picashu.repository.PokemonApiRepository
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PokemonApiViewModel(app: Application)  : AndroidViewModel(app) {

    private var pokemonApiRepository = PokemonApiRepository(app)

    val response : LiveData<PokemonListeResponse>

    init {
        response = pokemonApiRepository.response
    }

     fun getPokemonIds( limit : Int, offset : Int) {
         viewModelScope.launch {
             pokemonApiRepository.getPokemonID(limit, offset)
         }
    }

}