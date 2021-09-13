package com.example.picashu.model;

import com.google.gson.annotations.SerializedName;

public class Prices{

	@SerializedName("avg30")
	private double avg30;

	@SerializedName("reverseHoloSell")
	private Object reverseHoloSell;

	@SerializedName("reverseHoloLow")
	private Object reverseHoloLow;

	@SerializedName("averageSellPrice")
	private double averageSellPrice;

	@SerializedName("reverseHoloAvg7")
	private double reverseHoloAvg7;

	@SerializedName("germanProLow")
	private Object germanProLow;

	@SerializedName("avg7")
	private double avg7;

	@SerializedName("trendPrice")
	private double trendPrice;

	@SerializedName("suggestedPrice")
	private Object suggestedPrice;

	@SerializedName("avg1")
	private double avg1;

	@SerializedName("reverseHoloTrend")
	private double reverseHoloTrend;

	@SerializedName("lowPrice")
	private double lowPrice;

	@SerializedName("reverseHoloAvg30")
	private double reverseHoloAvg30;

	@SerializedName("lowPriceExPlus")
	private double lowPriceExPlus;

	@SerializedName("reverseHoloAvg1")
	private double reverseHoloAvg1;

	@SerializedName("holofoil")
	private Holofoil holofoil;

	@SerializedName("reverseHolofoil")
	private ReverseHolofoil reverseHolofoil;

	@SerializedName("normal")
	private Normal normal;

	@SerializedName("1stEditionNormal")
	private JsonMember1stEditionNormal jsonMember1stEditionNormal;

	@SerializedName("1stEditionHolofoil")
	private JsonMember1stEditionHolofoil jsonMember1stEditionHolofoil;

	public double getAvg30(){
		return avg30;
	}

	public Object getReverseHoloSell(){
		return reverseHoloSell;
	}

	public Object getReverseHoloLow(){
		return reverseHoloLow;
	}

	public double getAverageSellPrice(){
		return averageSellPrice;
	}

	public double getReverseHoloAvg7(){
		return reverseHoloAvg7;
	}

	public Object getGermanProLow(){
		return germanProLow;
	}

	public double getAvg7(){
		return avg7;
	}

	public double getTrendPrice(){
		return trendPrice;
	}

	public Object getSuggestedPrice(){
		return suggestedPrice;
	}

	public double getAvg1(){
		return avg1;
	}

	public double getReverseHoloTrend(){
		return reverseHoloTrend;
	}

	public double getLowPrice(){
		return lowPrice;
	}

	public double getReverseHoloAvg30(){
		return reverseHoloAvg30;
	}

	public double getLowPriceExPlus(){
		return lowPriceExPlus;
	}

	public double getReverseHoloAvg1(){
		return reverseHoloAvg1;
	}

	public Holofoil getHolofoil(){
		return holofoil;
	}

	public ReverseHolofoil getReverseHolofoil(){
		return reverseHolofoil;
	}

	public Normal getNormal(){
		return normal;
	}

	public JsonMember1stEditionNormal getJsonMember1stEditionNormal(){
		return jsonMember1stEditionNormal;
	}

	public JsonMember1stEditionHolofoil getJsonMember1stEditionHolofoil(){
		return jsonMember1stEditionHolofoil;
	}
}