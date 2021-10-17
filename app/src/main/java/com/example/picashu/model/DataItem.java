package com.example.picashu.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("supertype")
	private String supertype;

	@SerializedName("types")
	private List<String> types;

	@SerializedName("images")
	private Images images;

	@SerializedName("retreatCost")
	private List<String> retreatCost;

	@SerializedName("set")
	private Set set;

	@SerializedName("artist")
	private String artist;

	@SerializedName("hp")
	private String hp;

	@SerializedName("convertedRetreatCost")
	private int convertedRetreatCost;

	@SerializedName("legalities")
	private Legalities legalities;

	@SerializedName("rules")
	private List<String> rules;

	@SerializedName("evolvesFrom")
	private String evolvesFrom;

	@SerializedName("tcgplayer")
	private Tcgplayer tcgplayer;

	@SerializedName("subtypes")
	private List<String> subtypes;

	@SerializedName("number")
	private String number;

	@SerializedName("attacks")
	private List<AttacksItem> attacks;

	@SerializedName("nationalPokedexNumbers")
	private List<Integer> nationalPokedexNumbers;

	@SerializedName("weaknesses")
	private List<WeaknessesItem> weaknesses;

	@SerializedName("name")
	private String name;

	@SerializedName("cardmarket")
	private Cardmarket cardmarket;

	@SerializedName("id")
	private String id;

	@SerializedName("rarity")
	private String rarity;

	@SerializedName("evolvesTo")
	private List<String> evolvesTo;

	@SerializedName("flavorText")
	private String flavorText;

	@SerializedName("abilities")
	private List<AbilitiesItem> abilities;

	@SerializedName("level")
	private String level;

	@SerializedName("resistances")
	private List<ResistancesItem> resistances;

	private Boolean addBtnVisible;

	private Boolean deleteBtnVisible;

	private Boolean tradeBtnVisible;

	public Boolean getTradeBtnVisible() {
		return tradeBtnVisible;
	}

	public void setTradeBtnVisible(Boolean tradeBtnVisible) {
		this.tradeBtnVisible = tradeBtnVisible;
	}

	public Boolean getAddBtnVisible() {
		return addBtnVisible;
	}

	public void setAddBtnVisible(Boolean addBtnVisible) {
		this.addBtnVisible = addBtnVisible;
	}

	public Boolean getDeleteBtnVisible() {
		return deleteBtnVisible;
	}

	public void setDeleteBtnVisible(Boolean deleteBtnVisible) {
		this.deleteBtnVisible = deleteBtnVisible;
	}



	public String getSupertype(){
		return supertype;
	}

	public List<String> getTypes(){
		return types;
	}

	public Images getImages(){
		return images;
	}

	public List<String> getRetreatCost(){
		return retreatCost;
	}

	public Set getSet(){
		return set;
	}

	public String getArtist(){
		return artist;
	}

	public String getHp(){
		return hp;
	}

	public int getConvertedRetreatCost(){
		return convertedRetreatCost;
	}

	public Legalities getLegalities(){
		return legalities;
	}

	public List<String> getRules(){
		return rules;
	}

	public String getEvolvesFrom(){
		return evolvesFrom;
	}

	public Tcgplayer getTcgplayer(){
		return tcgplayer;
	}

	public List<String> getSubtypes(){
		return subtypes;
	}

	public String getNumber(){
		return number;
	}

	public List<AttacksItem> getAttacks(){
		return attacks;
	}

	public List<Integer> getNationalPokedexNumbers(){
		return nationalPokedexNumbers;
	}

	public List<WeaknessesItem> getWeaknesses(){
		return weaknesses;
	}

	public String getName(){
		return name;
	}

	public Cardmarket getCardmarket(){
		return cardmarket;
	}

	public String getId(){
		return id;
	}

	public String getRarity(){
		return rarity;
	}

	public List<String> getEvolvesTo(){
		return evolvesTo;
	}

	public String getFlavorText(){
		return flavorText;
	}

	public List<AbilitiesItem> getAbilities(){
		return abilities;
	}

	public String getLevel(){
		return level;
	}

	public List<ResistancesItem> getResistances(){
		return resistances;
	}
}