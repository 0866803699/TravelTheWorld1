package com.example.traveltheworld.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.traveltheworld.Activityes.DetailsActivity;
import com.example.traveltheworld.Activityes.DetailsVietNam;
import com.example.traveltheworld.R;
import com.example.traveltheworld.model.RecentData;
import com.example.traveltheworld.model.RecentResult;
import com.example.traveltheworld.model.TopPlaceResult;
import com.example.traveltheworld.model.TopPlacesData;

import java.util.List;

public class TopPlacesAdapter extends RecyclerView.Adapter<TopPlacesAdapter.TopPlacesViewHolder> {
    Context context;
    List<TopPlacesData> topPlacesDataList;
    private final TopPlaceResult topPlaceResult = new TopPlaceResult();

    public TopPlacesAdapter(Context context, List<TopPlacesData> topPlacesDataList) {
        this.context = context;
        this.topPlacesDataList = topPlacesDataList;
    }

    @NonNull
    @Override
    public TopPlacesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.top_places_item,parent,false);
        return new TopPlacesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopPlacesViewHolder holder, @SuppressLint("RecyclerView") int position) {
        TopPlacesData topPlacesData = topPlacesDataList.get(position);
        if (topPlacesData== null){
            return;
        }
        holder.countryName.setText(topPlacesDataList.get(position).getCountryName());
        holder.placeName.setText(topPlacesDataList.get(position).getPlaceName());
        holder.price.setText(topPlacesDataList.get(position).getPrice());
        Glide.with(context).load(topPlacesDataList.get(position).getImageUrl()).into(holder.placeImage);

       // holder.placeImage.setImageResource(topPlacesDataList.get(position).getImageUrl());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context, DetailsVietNam.class);
                intent.putExtra("place", topPlaceResult);
                intent.putExtra("pos2", position);
                intent.putExtra("data2", topPlacesDataList.get(position));
                intent.putExtra("des",topPlacesData.getDescription());
                intent.putExtra("time",topPlacesData.getTime());
                intent.putExtra("ratings",topPlacesData.getRatings());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return topPlacesDataList.size();
    }

    public static final class TopPlacesViewHolder extends RecyclerView.ViewHolder{
        ImageView placeImage;
        TextView placeName,countryName,price;
        public TopPlacesViewHolder(@NonNull View itemView) {
            super(itemView);
            placeImage= itemView.findViewById(R.id.im_place);
            placeName= itemView.findViewById(R.id.tv_place_name);
            countryName= itemView.findViewById(R.id.tv_country_name);
            price= itemView.findViewById(R.id.tv_price);
        }
    }


}
