package com.example.picashu.model

import com.google.gson.annotations.SerializedName

data class PokemonListeResponse(

	@field:SerializedName("next")
	val next: String? = null,

	@field:SerializedName("previous")
	val previous: Any? = null,

	@field:SerializedName("count")
	val count: Int? = null,

	@field:SerializedName("results")
	val results: MutableList<ResultsItem?>? = null
)

data class ResultsItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("url")
	val url: String? = null

//			public int getNumber() {
//		String[] urlPartes = url.split("/");
//		return Integer.parseInt(urlPartes[urlPartes.length - 1]);
	//}

)
