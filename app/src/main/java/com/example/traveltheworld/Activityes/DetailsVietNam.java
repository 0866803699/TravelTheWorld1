package com.example.traveltheworld.Activityes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.traveltheworld.R;
import com.example.traveltheworld.databinding.ActivityDetailsVietNamBinding;
import com.example.traveltheworld.model.RecentData;
import com.example.traveltheworld.model.RecentResult;
import com.example.traveltheworld.model.TopPlaceResult;
import com.example.traveltheworld.model.TopPlacesData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class DetailsVietNam extends AppCompatActivity {
    ActivityDetailsVietNamBinding binding;
    TopPlacesData topPlacesData;
    int pos;
    TopPlaceResult result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_details_viet_nam);
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

        topPlacesData = (TopPlacesData) getIntent().getExtras().getSerializable("data2");


        result = new Gson().fromJson(getTopPlace(), TopPlaceResult.class);
        pos = getIntent().getExtras().getInt("pos2");
        String description = getIntent().getStringExtra("des");
        String start = getIntent().getStringExtra("ratings");
        String time = getIntent().getStringExtra("time");

        binding.tvName.setText(topPlacesData.getPlaceName());
        binding.tvCountryName.setText(topPlacesData.getCountryName());
        binding.tvPrice.setText(topPlacesData.getPrice());
        Picasso
                .with(DetailsVietNam.this)
                .load(Uri.parse(topPlacesData.getImageUrl()))
                .into(binding.imBg);
        binding.tvDescription.setText(description);
        binding.tvStar.setText(start);
        binding.tvTime.setText(time);
        binding.btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAddItem(topPlacesData);
            }
        });
    }
    private void onClickAddItem(TopPlacesData topPlacesData) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef =database.getReference("booking");
        String name= String.valueOf(topPlacesData.getPlaceName());
        String idUser = user.getUid();
        myRef.child(idUser).child(name).setValue(topPlacesData, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(getBaseContext(), "Đặt chuyến thành công, chúng tôi sẽ sớm liên hệ với bạn", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String getTopPlace() {
        SharedPreferences sp2 = getSharedPreferences("recent", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        if (sp2.contains("data2")) {
            return sp2.getString("data2", null);
        } else {
            return null;
        }

    }
}