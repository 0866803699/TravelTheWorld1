package com.example.traveltheworld.Activityes;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import com.example.traveltheworld.R;
import com.example.traveltheworld.adapter.HotelAdapter;
import com.example.traveltheworld.databinding.ActivityHotelInforBinding;
import com.example.traveltheworld.model.Hotel;
import com.example.traveltheworld.model.HotelResult;
import com.example.traveltheworld.model.TopPlacesData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HotelInfo extends AppCompatActivity {

    ActivityHotelInforBinding binding;
    Hotel hotel;
    int pos;
    HotelResult hotelResult;
    String description;
    String start;
    String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hotel_infor);
        binding.imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        hotel = (Hotel) getIntent().getExtras().getSerializable("data");
        description = getIntent().getStringExtra("des");
        start = getIntent().getStringExtra("ratings");
        time = getIntent().getStringExtra("time");

        hotelResult = new Gson().fromJson(getHotels(), HotelResult.class);
        pos = getIntent().getExtras().getInt("pos");
        String description = getIntent().getStringExtra("des");
        binding.tvName.setText(hotel.getPlaceName());
        Picasso
                .with(HotelInfo.this)
                .load(Uri.parse(hotel.getImageUrl()))
                .into(binding.imBg);
        binding.tvDescription.setText(description);
        binding.tvCountryName.setText(hotel.getCountryName());
        binding.tvPrice.setText(hotel.getPrice());
        binding.tvPrice.setText(hotel.getPrice());
        binding.tvStar.setText(start);
        binding.tvTime.setText(time);


        binding.btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAddItem(hotel);
            }
        });
    }
    private void onClickAddItem(Hotel hotel) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef =database.getReference("booking");
        String name= String.valueOf(hotel.getPlaceName());
        String idUser = user.getUid();
        myRef.child(idUser).child(name).setValue(hotel, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(getBaseContext(), "Đặt chuyến thành công, chúng tôi sẽ sớm liên hệ với bạn", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public String getHotels() {
        SharedPreferences sp = getSharedPreferences("hotel", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        if (sp.contains("data")) {
            return sp.getString("data", null);
        } else {
            return null;
        }

    }
}
