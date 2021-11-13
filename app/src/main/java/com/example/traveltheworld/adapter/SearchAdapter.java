package com.example.traveltheworld.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.traveltheworld.Activityes.DetailsActivity;
import com.example.traveltheworld.Activityes.DetailsSearchActivity;
import com.example.traveltheworld.R;
import com.example.traveltheworld.model.Book;
import com.example.traveltheworld.model.RecentResult;
import com.example.traveltheworld.model.Search;
import com.example.traveltheworld.model.SearchResult;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> implements Filterable {
    Context context;
    List<Search> searchList ;
    List<Search> searchListOld;
    private final SearchResult searchResult = new SearchResult();

    public SearchAdapter(Context context, List<Search> searchList) {
        this.context = context;
        this.searchList = searchList;
        this.searchListOld= searchList;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search,parent,false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, @SuppressLint("RecyclerView") int position) {
                Search search = searchList.get(position);
                if (search == null){
                    return;
                }
                holder.placeName.setText(searchList.get(position).getPlaceName());
                holder.countryName.setText(searchList.get(position).getCountryName());
                holder.price.setText(searchList.get(position).getPrice());
        Glide.with(context).load(searchList.get(position).getImageUrl()).into(holder.placeImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsSearchActivity.class);
                intent.putExtra("place", searchResult);
                intent.putExtra("pos1", position);
                intent.putExtra("data1", searchList.get(position));
                intent.putExtra("des",search.getDescription());
                intent.putExtra("time",search.getTime());
                intent.putExtra("ratings",search.getRatings());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (searchList != null){
            return searchList.size();
        }
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if (strSearch.isEmpty()){
                    searchList= searchListOld;
                }
                else {
                    List<Search> list= new ArrayList<>();
                    for(Search search : searchListOld){
                        if (search.getPlaceName().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(search);
                        }
                    }
                    searchList = list;
                }
                FilterResults filterResults= new FilterResults();
                filterResults.values = searchList;
                return filterResults;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    searchList = (List<Search>) filterResults.values;
                    notifyDataSetChanged();
            }
        };
    }

    public static final class SearchViewHolder extends RecyclerView.ViewHolder{
        ImageView placeImage;
        TextView placeName,countryName,price;
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            placeImage= itemView.findViewById(R.id.im_place);
            placeName= itemView.findViewById(R.id.tv_place_name);
            countryName= itemView.findViewById(R.id.tv_country_name);
            price= itemView.findViewById(R.id.tv_price);
        }
    }
}
