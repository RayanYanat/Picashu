package com.example.picashu.model;

import com.google.gson.annotations.SerializedName;

public class AbilitiesItem{

	@SerializedName("name")
	private String name;

	@SerializedName("text")
	private String text;

	@SerializedName("type")
	private String type;

	public String getName(){
		return name;
	}

	public String getText(){
		return text;
	}

	public String getType(){
		return type;
	}
}