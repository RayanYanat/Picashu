package com.example.picashu.model;

import com.google.gson.annotations.SerializedName;

public class Set{

	@SerializedName("total")
	private int total;

	@SerializedName("images")
	private Images images;

	@SerializedName("printedTotal")
	private int printedTotal;

	@SerializedName("ptcgoCode")
	private String ptcgoCode;

	@SerializedName("releaseDate")
	private String releaseDate;

	@SerializedName("series")
	private String series;

	@SerializedName("name")
	private String name;

	@SerializedName("legalities")
	private Legalities legalities;

	@SerializedName("id")
	private String id;

	@SerializedName("updatedAt")
	private String updatedAt;

	public int getTotal(){
		return total;
	}

	public Images getImages(){
		return images;
	}

	public int getPrintedTotal(){
		return printedTotal;
	}

	public String getPtcgoCode(){
		return ptcgoCode;
	}

	public String getReleaseDate(){
		return releaseDate;
	}

	public String getSeries(){
		return series;
	}

	public String getName(){
		return name;
	}

	public Legalities getLegalities(){
		return legalities;
	}

	public String getId(){
		return id;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}
}