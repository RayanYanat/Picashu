package com.example.picashu.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.picashu.model.PokemonListeResponse
import com.example.picashu.repository.FirebaseRepository
import com.example.picashu.repository.PokemonApiRepository
import kotlinx.coroutines.launch

class PokemonListFragmentViewModel : ViewModel() {

    private var pokemonApiRepository = PokemonApiRepository()
    private var firebaseRepository = FirebaseRepository()

    val response : MutableLiveData<PokemonListeResponse> = pokemonApiRepository.response

    fun getPokemonIds( limit : Int, offset : Int) {
        viewModelScope.launch {
            pokemonApiRepository.getPokemonID(limit, offset)
        }
    }

}