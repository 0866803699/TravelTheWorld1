package com.example.traveltheworld.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.traveltheworld.R;
import com.example.traveltheworld.model.Book;
import com.example.traveltheworld.model.RecentData;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    Context context;
    List<Book> bookList;
    IClickListener mIClickListener;
    public interface IClickListener{
        void iClickDeleteListenerItem(Book book);
    }

    public BookAdapter(Context context, List<Book> bookList, BookAdapter.IClickListener mIClickListener) {
        this.context = context;
        this.bookList = bookList;
        this.mIClickListener= mIClickListener;
    }


    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_book,parent,false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book= bookList.get(position);
        if (book== null){
            return;
        }
        holder.placeName.setText(bookList.get(position).getPlaceName());
        holder.countryName.setText(bookList.get(position).getCountryName());
        holder.price.setText(bookList.get(position).getPrice());
        holder.time.setText(bookList.get(position).getTime());
        Glide.with(context).load(bookList.get(position).getImageUrl()).into(holder.placeImage);
        holder.btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIClickListener.iClickDeleteListenerItem(book);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (bookList!= null){
            return bookList.size();
        }
        return 0;

    }

    public static class BookViewHolder extends RecyclerView.ViewHolder{
        ImageView placeImage;
        TextView placeName,countryName,price,time;
        Button btnHuy;
        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            placeImage= itemView.findViewById(R.id.im_place);
            placeName= itemView.findViewById(R.id.tv_place_name);
            countryName= itemView.findViewById(R.id.tv_country_name);
            price= itemView.findViewById(R.id.tv_price);
            time = itemView.findViewById(R.id.tv_time);
            btnHuy = itemView.findViewById(R.id.btn_huy);
        }
    }
}
