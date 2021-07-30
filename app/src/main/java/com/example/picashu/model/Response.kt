package com.example.picashu.model

import com.google.gson.annotations.SerializedName

data class Response(

	@field:SerializedName("effect_entries")
	val effectEntries: List<EffectEntriesItem?>? = null,

	@field:SerializedName("generation")
	val generation: Generation? = null,

	@field:SerializedName("is_main_series")
	val isMainSeries: Boolean? = null,

	@field:SerializedName("names")
	val names: List<NamesItem?>? = null,

	@field:SerializedName("pokemon")
	val pokemon: List<PokemonItem?>? = null,

	@field:SerializedName("flavor_text_entries")
	val flavorTextEntries: List<FlavorTextEntriesItem?>? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("effect_changes")
	val effectChanges: List<EffectChangesItem?>? = null
)

data class EffectChangesItem(

	@field:SerializedName("effect_entries")
	val effectEntries: List<EffectEntriesItem?>? = null,

	@field:SerializedName("version_group")
	val versionGroup: VersionGroup? = null
)

data class Pokemon(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("url")
	val url: String? = null
)

data class Generation(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("url")
	val url: String? = null
)

data class VersionGroup(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("url")
	val url: String? = null
)

data class EffectEntriesItem(

	@field:SerializedName("effect")
	val effect: String? = null,

	@field:SerializedName("language")
	val language: Language? = null
)

data class PokemonItem(

	@field:SerializedName("pokemon")
	val pokemon: Pokemon? = null,

	@field:SerializedName("is_hidden")
	val isHidden: Boolean? = null,

	@field:SerializedName("slot")
	val slot: Int? = null
)

data class FlavorTextEntriesItem(

	@field:SerializedName("version_group")
	val versionGroup: VersionGroup? = null,

	@field:SerializedName("language")
	val language: Language? = null,

	@field:SerializedName("flavor_text")
	val flavorText: String? = null
)

data class NamesItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("language")
	val language: Language? = null
)

data class Language(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("url")
	val url: String? = null
)
