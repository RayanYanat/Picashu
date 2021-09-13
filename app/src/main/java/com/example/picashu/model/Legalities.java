package com.example.picashu.model;

import com.google.gson.annotations.SerializedName;

public class Legalities{

	@SerializedName("standard")
	private String standard;

	@SerializedName("expanded")
	private String expanded;

	@SerializedName("unlimited")
	private String unlimited;

	public String getStandard(){
		return standard;
	}

	public String getExpanded(){
		return expanded;
	}

	public String getUnlimited(){
		return unlimited;
	}
}