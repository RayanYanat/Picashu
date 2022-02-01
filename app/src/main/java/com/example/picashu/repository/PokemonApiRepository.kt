package com.example.picashu.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.picashu.PokemonApiService
import com.example.picashu.model.CardResponse.ResponseCard
import com.example.picashu.model.CardSetResponse.CardSet
import com.example.picashu.model.DataItem
import com.example.picashu.model.PokemonListeResponse
import com.example.picashu.model.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokemonApiRepository() {

    val response = MutableLiveData<PokemonListeResponse>()
    val cardResponse = MutableLiveData<Response>()
    val cardIdResponse = MutableLiveData<ResponseCard>()
    val setResponse = MutableLiveData<CardSet>()


    fun getPokemonID(limit: Int, offset: Int): MutableLiveData<PokemonListeResponse> {

        val responseData = MutableLiveData<PokemonListeResponse>()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokemonApiService::class.java)

        //val service = retrofit.create(PokemonApiService::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            val apiResponse = retrofit.fetchPokemonList(limit,offset)
            if (apiResponse.isSuccessful) {
//                Toast.makeText(application, "Success accessing the API", Toast.LENGTH_SHORT).show()
                Log.d("LolApiRepository", "LolApiRepository:$apiResponse")
                response.postValue(apiResponse.body())
                Log.d("LolApiRepository", "PokeApiRepository: $response" )
                responseData.postValue(apiResponse.body())
            } else {
                Log.d("LolApiRepository", "LolApiRepository:" + apiResponse.errorBody().toString())
//                Toast.makeText(application, "Error wile accessing the API", Toast.LENGTH_SHORT)
//                    .show()
            }
        }

        return responseData
    }

    fun getPokemonCard (name : String, apiKey : String):MutableLiveData<Response>
    {

        val responseData = MutableLiveData<Response>()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.pokemontcg.io/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokemonApiService::class.java)

        //val service = retrofit.create(PokemonApiService::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            val apiResponse = retrofit.fetchPokemonCardList(name,apiKey)
            if (apiResponse.isSuccessful) {
//                Toast.makeText(application, "Success accessing the API", Toast.LENGTH_SHORT).show()
                Log.d("pokeApiCard", "LolApiRepository:$apiResponse")
                cardResponse.postValue(apiResponse.body())
                Log.d("pokeApiCard", "PokeApiRepository: $response" )
                responseData.postValue(apiResponse.body())
            } else {
                Log.d("pokeApiCard", "LolApiRepository:" + apiResponse.errorBody().toString())
//                Toast.makeText(application, "Error wile accessing the API", Toast.LENGTH_SHORT)
//                    .show()
            }
        }

        return responseData
    }

    fun getPokemonCardWithId (id : String):MutableLiveData<ResponseCard>
    {

        val responseData = MutableLiveData<ResponseCard>()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.pokemontcg.io/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokemonApiService::class.java)

        //val service = retrofit.create(PokemonApiService::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            val apiResponse = retrofit.fetchPokemonCard(id)
            if (apiResponse.isSuccessful) {
//                Toast.makeText(application, "Success accessing the API", Toast.LENGTH_SHORT).show()
                Log.d("pokeApiCardID", "pokeApiCardID:$apiResponse")
                cardIdResponse.postValue(apiResponse.body())
                Log.d("pokeApiCardID", "pokeApiCardID: $response" )
                responseData.postValue(apiResponse.body())
            } else {
                Log.d("pokeApiCardID", "pokeApiCardID:" + apiResponse.errorBody().toString())
//                Toast.makeText(application, "Error wile accessing the API", Toast.LENGTH_SHORT)
//                    .show()
            }
        }

        return responseData
    }

    fun getPokemonSet (id : String, apiKey : String ):MutableLiveData<CardSet>
    {

        val responseData = MutableLiveData<CardSet>()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.pokemontcg.io/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokemonApiService::class.java)

        //val service = retrofit.create(PokemonApiService::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            val apiResponse = retrofit.fetchPokemonSetsList(id,apiKey)
            if (apiResponse.isSuccessful) {
//                Toast.makeText(application, "Success accessing the API", Toast.LENGTH_SHORT).show()
                Log.d("pokeApiCardID", "pokeApiCardID:$apiResponse")
                setResponse.postValue(apiResponse.body())
                Log.d("pokeApiCardID", "pokeApiCardID: $response" )
                responseData.postValue(apiResponse.body())
            } else {
                Log.d("pokeApiCardID", "pokeApiCardID:" + apiResponse.errorBody().toString())
//                Toast.makeText(application, "Error wile accessing the API", Toast.LENGTH_SHORT)
//                    .show()
            }
        }

        return responseData
    }
}



