package com.example.picashu.model;

import com.google.gson.annotations.SerializedName;

public class Cardmarket{

	@SerializedName("prices")
	private Prices prices;

	@SerializedName("url")
	private String url;

	@SerializedName("updatedAt")
	private String updatedAt;

	public Prices getPrices(){
		return prices;
	}

	public String getUrl(){
		return url;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}
}