package com.example.picashu

import com.example.picashu.model.PokemonListeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonApiService {

    @GET("pokemon")
     fun fetchPokemonList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): Call<PokemonListeResponse>

}