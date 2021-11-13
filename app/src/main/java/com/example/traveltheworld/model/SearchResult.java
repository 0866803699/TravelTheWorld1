package com.example.traveltheworld.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SearchResult implements Serializable {
    @SerializedName("place")
    @Expose
    private List<Search> searchList = null;

    public List<Search> searchList() {
        return searchList;
    }

    public void setSearchData(List<Search> searchList) {
        this.searchList = searchList;
    }
}
