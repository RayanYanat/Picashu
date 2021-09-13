package com.example.picashu.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Response{

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("count")
	private int count;

	@SerializedName("pageSize")
	private int pageSize;

	@SerializedName("page")
	private int page;

	@SerializedName("totalCount")
	private int totalCount;

	public List<DataItem> getData(){
		return data;
	}

	public int getCount(){
		return count;
	}

	public int getPageSize(){
		return pageSize;
	}

	public int getPage(){
		return page;
	}

	public int getTotalCount(){
		return totalCount;
	}
}