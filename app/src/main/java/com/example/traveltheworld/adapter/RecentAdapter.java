package com.example.traveltheworld.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.traveltheworld.Activityes.DetailsActivity;
import com.example.traveltheworld.R;
import com.example.traveltheworld.model.HotelResult;
import com.example.traveltheworld.model.RecentData;
import com.example.traveltheworld.model.RecentResult;
import com.example.traveltheworld.model.Search;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

public class RecentAdapter extends RecyclerView.Adapter<RecentAdapter.RecentViewHolder> {
    Context context;
    List<RecentData> recentDataList;
    private final RecentResult recentResult = new RecentResult();

    public RecentAdapter(Context context, List<RecentData> recentDataList) {
        this.context = context;
        this.recentDataList = recentDataList;
    }


    @NonNull
    @Override
    public RecentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recent_item,parent,false);
        return new RecentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentViewHolder holder, @SuppressLint("RecyclerView") int position) {
        RecentData recentData= recentDataList.get(position);
        if (recentData== null){
            return;
        }


        holder.placeName.setText(recentDataList.get(position).getPlaceName());
        holder.countryName.setText(recentDataList.get(position).getCountryName());
        holder.price.setText(recentDataList.get(position).getPrice());
        Glide.with(context).load(recentDataList.get(position).getImageUrl()).into(holder.placeImage);
//        holder.placeImage.setImageResource(recentDataList.get(position).getImageUrl());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context, DetailsActivity.class);
                intent.putExtra("place", recentResult);
                intent.putExtra("pos1", position);
                intent.putExtra("data1", recentDataList.get(position));
                intent.putExtra("des",recentData.getDescription());
                intent.putExtra("time",recentData.getTime());
                intent.putExtra("ratings",recentData.getRatings());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recentDataList.size();
    }

    public static final class RecentViewHolder extends RecyclerView.ViewHolder{
        ImageView placeImage;
        TextView placeName,countryName,price;
        public RecentViewHolder(@NonNull View itemView) {
            super(itemView);
            placeImage= itemView.findViewById(R.id.im_place);
            placeName= itemView.findViewById(R.id.tv_place_name);
            countryName= itemView.findViewById(R.id.tv_country_name);
            price= itemView.findViewById(R.id.tv_price);
        }
    }
}
