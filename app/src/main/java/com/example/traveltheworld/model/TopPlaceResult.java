package com.example.traveltheworld.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TopPlaceResult implements Serializable {

    @SerializedName("place")
    @Expose
    private List<TopPlacesData> topPlacesDataList = null;

    public List<TopPlacesData> topPlacesDataList() {
        return topPlacesDataList;
    }

    public void setTopPlaceData(List<TopPlacesData> topPlacesDataList) {
        this.topPlacesDataList = topPlacesDataList;
    }
}
