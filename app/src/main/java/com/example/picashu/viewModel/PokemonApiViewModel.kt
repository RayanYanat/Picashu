package com.example.picashu.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.picashu.PokemonApiService
import com.example.picashu.model.PokemonListeResponse
import com.example.picashu.repository.PokemonApiRepository

class PokemonApiViewModel(app: Application)  : AndroidViewModel(app) {

    private var PokemonApiRepository = PokemonApiRepository(app)

    val response : LiveData<PokemonListeResponse>

    init {
        response = PokemonApiRepository.response
    }

    fun getPokemonIds( limit : Int, offset : Int) {
        PokemonApiRepository.getPokemonID(limit, offset)
    }
}