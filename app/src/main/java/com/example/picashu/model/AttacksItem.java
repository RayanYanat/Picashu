package com.example.picashu.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class AttacksItem{

	@SerializedName("damage")
	private String damage;

	@SerializedName("cost")
	private List<String> cost;

	@SerializedName("name")
	private String name;

	@SerializedName("text")
	private String text;

	@SerializedName("convertedEnergyCost")
	private int convertedEnergyCost;

	public String getDamage(){
		return damage;
	}

	public List<String> getCost(){
		return cost;
	}

	public String getName(){
		return name;
	}

	public String getText(){
		return text;
	}

	public int getConvertedEnergyCost(){
		return convertedEnergyCost;
	}
}