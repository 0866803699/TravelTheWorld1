package com.example.traveltheworld.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.traveltheworld.Activityes.DetailsActivity;
import com.example.traveltheworld.Activityes.HotelInfo;
import com.example.traveltheworld.R;
import com.example.traveltheworld.model.Hotel;
import com.example.traveltheworld.model.HotelResult;
import com.example.traveltheworld.model.RecentData;
import com.google.gson.Gson;


import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelViewHolder> {
    Context context;
    List<Hotel> hotelsDataList;
    private HotelResult hotelResult = new HotelResult();

    public HotelAdapter(Context context, List<Hotel> hotelsDataList) {
        this.context = context;
        this.hotelsDataList = hotelsDataList;
    }

    @NonNull
    @Override
    public HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.hotel_item,parent,false);
        return new HotelViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull HotelViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Hotel hotel= hotelsDataList.get(position);
        if (hotel== null){
            return;
        }

        holder.hotelName.setText(hotelsDataList.get(position).getPlaceName());
        holder.countryname.setText(hotelsDataList.get(position).getCountryName());
        holder.ratings.setText(hotelsDataList.get(position).getRatings());
        Glide.with(context).load(hotelsDataList.get(position).getImageUrl()).into(holder.hotelImage);
//        holder.placeImage.setImageResource(recentDataList.get(position).getImageUrl());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context, HotelInfo.class);
                intent.putExtra("hotels", hotelResult);
                intent.putExtra("pos", position);
                intent.putExtra("data", hotelsDataList.get(position));
                intent.putExtra("des",hotel.getDescription());
                intent.putExtra("time",hotel.getTime());
                intent.putExtra("ratings",hotel.getRatings());
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return hotelsDataList.size();
    }

    public static final class HotelViewHolder extends RecyclerView.ViewHolder{
        ImageView hotelImage;
        TextView hotelName,countryname,ratings;
        public HotelViewHolder(@NonNull View itemView) {
            super(itemView);
            hotelImage= itemView.findViewById(R.id.hotelImage);
            hotelName= itemView.findViewById(R.id.hotelName);
            countryname= itemView.findViewById(R.id.tagsList);
            ratings= itemView.findViewById(R.id.ratings);

        }
    }
}
