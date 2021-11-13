package com.example.traveltheworld.api;

import com.example.traveltheworld.model.RecentData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    //http://demo6990916.mockable.io/getRecentData


    @GET("getRecentData")
    Call<List<RecentData>> getListRecentData();
}
