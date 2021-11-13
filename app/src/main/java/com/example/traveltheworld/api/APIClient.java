package com.example.traveltheworld.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

     public  static final String BASE_URL = "https://demo6050912.mockable.io/";
     public static retrofit2.Retrofit retrofit;

   public static retrofit2.Retrofit getRetrofitClient(String baseUrl) {
       if (retrofit== null){
           retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
       }
       return retrofit;
   }
   public static ApiService getListRecentData(){
       return APIClient.getRetrofitClient(BASE_URL).create(ApiService.class);
   }
}
