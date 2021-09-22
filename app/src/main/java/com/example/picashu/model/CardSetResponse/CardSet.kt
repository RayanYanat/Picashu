package com.example.picashu.model.CardSetResponse

import com.google.gson.annotations.SerializedName

data class CardSet(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("count")
	val count: Int? = null,

	@field:SerializedName("pageSize")
	val pageSize: Int? = null,

	@field:SerializedName("page")
	val page: Int? = null,

	@field:SerializedName("totalCount")
	val totalCount: Int? = null
)

data class Images(

	@field:SerializedName("symbol")
	val symbol: String? = null,

	@field:SerializedName("logo")
	val logo: String? = null
)

data class DataItem(

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("images")
	val images: Images? = null,

	@field:SerializedName("printedTotal")
	val printedTotal: Int? = null,

	@field:SerializedName("ptcgoCode")
	val ptcgoCode: String? = null,

	@field:SerializedName("releaseDate")
	val releaseDate: String? = null,

	@field:SerializedName("series")
	val series: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("legalities")
	val legalities: Legalities? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class Legalities(

	@field:SerializedName("standard")
	val standard: String? = null,

	@field:SerializedName("expanded")
	val expanded: String? = null,

	@field:SerializedName("unlimited")
	val unlimited: String? = null
)
