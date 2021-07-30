package com.example.picashu.repository

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.picashu.PokemonApiService
import com.example.picashu.model.PokemonListeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokemonApiRepository(val application: Application) {

    val response = MutableLiveData<PokemonListeResponse>()

    fun getPokemonID(limit : Int, offset : Int): MutableLiveData<PokemonListeResponse> {


        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(PokemonApiService::class.java)



        service.fetchPokemonList(limit, offset).enqueue(object : Callback<PokemonListeResponse> {
            override fun onFailure(call: Call<PokemonListeResponse>, t: Throwable) {
                Toast.makeText(application, "Error wile accessing the API$t", Toast.LENGTH_SHORT)
                    .show()
                Log.d("LolApiRepository", "LolApiRepository:$t")

            }

            override fun onResponse(call: Call<PokemonListeResponse>, resp: Response<PokemonListeResponse>) {
                Log.d("LolApiRepository", "LolApiRepository:$resp")
                if (resp.body() != null) {
                    Toast.makeText(application, "Success accessing the API", Toast.LENGTH_SHORT)
                        .show()
                    response.value = resp.body()
                } else {
                    Log.d("LolApiRepository", "LolApiRepository:" + resp.errorBody().toString())
//                    Toast.makeText(application, "Error wile accessing the API", Toast.LENGTH_SHORT)
//                        .show()
                }
            }

        })
        return response
    }
}