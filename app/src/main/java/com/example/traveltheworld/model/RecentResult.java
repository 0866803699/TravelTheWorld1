package com.example.traveltheworld.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class RecentResult implements Serializable {

    @SerializedName("place")
    @Expose
    private List<RecentData> recentDataList = null;

    public List<RecentData> recentDataList() {
        return recentDataList;
    }

    public void setRecentData(List<RecentData> recentDataList) {
        this.recentDataList = recentDataList;
    }
}
