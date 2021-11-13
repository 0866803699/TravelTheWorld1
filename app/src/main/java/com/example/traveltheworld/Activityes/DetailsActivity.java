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
import com.example.traveltheworld.databinding.ActivityDetailsBinding;
import com.example.traveltheworld.model.RecentData;
import com.example.traveltheworld.model.RecentResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {
    ActivityDetailsBinding binding;
    RecentData recentData;
    int pos;
    RecentResult result;
    String description;
    String start;
    String time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_details);
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

        recentData = (RecentData) getIntent().getExtras().getSerializable("data1");


        result = new Gson().fromJson(getRecent(), RecentResult.class);
        pos = getIntent().getExtras().getInt("pos1");
        description = getIntent().getStringExtra("des");
        start = getIntent().getStringExtra("ratings");
        time = getIntent().getStringExtra("time");

        binding.tvName.setText(recentData.getPlaceName());
        binding.tvCountryName.setText(recentData.getCountryName());
        binding.tvPrice.setText(recentData.getPrice());
        Picasso
                .with(DetailsActivity.this)
                .load(Uri.parse(recentData.getImageUrl()))
                .into(binding.imBg);
        binding.tvDescription.setText(description);
        binding.tvStar.setText(start);
        binding.tvTime.setText(time);
        binding.btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAddItem(recentData);
            }
        });
    }

    private void onClickAddItem(RecentData recentData) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef =database.getReference("booking");
        String name= String.valueOf(recentData.getPlaceName());
        String idUser = user.getUid();
        myRef.child(idUser).child(name).setValue(recentData, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(getBaseContext(), "Đặt chuyến thành công, chúng tôi sẽ sớm liên hệ với bạn", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String getRecent() {
        SharedPreferences sp1 = getSharedPreferences("recent", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        if (sp1.contains("data1")) {
            return sp1.getString("data1", null);
        } else {
            return null;
        }

    }
}