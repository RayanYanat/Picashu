package com.example.picashu

import com.example.picashu.model.CardResponse.ResponseCard
import com.example.picashu.model.CardSetResponse.CardSet
import com.example.picashu.model.DataItem
import com.example.picashu.model.PokemonListeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApiService {

    @GET("pokemon")
      suspend fun fetchPokemonList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): Response<PokemonListeResponse>

    @GET("cards?")
    suspend fun fetchPokemonCardList(
        @Query("q") q: String,
        @Query("api_key") key: String

    ): Response<com.example.picashu.model.Response>

    @GET("cards/{id}")
    suspend fun fetchPokemonCard(
        @Path("id") id : String,
    ): Response<ResponseCard>

    @GET("sets")
    suspend fun fetchPokemonSetsList(
        @Query("q") q: String,
        @Query("api_key") key: String

    ): Response<CardSet>
}