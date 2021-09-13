package com.example.picashu.model;

import com.google.gson.annotations.SerializedName;

public class WeaknessesItem{

	@SerializedName("type")
	private String type;

	@SerializedName("value")
	private String value;

	public String getType(){
		return type;
	}

	public String getValue(){
		return value;
	}
}