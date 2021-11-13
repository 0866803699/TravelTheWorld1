package com.example.traveltheworld.Activityes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.traveltheworld.R;
import com.example.traveltheworld.databinding.ActivityDetailsSearchBinding;
import com.example.traveltheworld.model.RecentData;
import com.example.traveltheworld.model.RecentResult;
import com.example.traveltheworld.model.Search;
import com.example.traveltheworld.model.SearchResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class DetailsSearchActivity extends AppCompatActivity {
    ActivityDetailsSearchBinding binding;
    Search search;
    int pos;
    SearchResult result;
    String description;
    String start;
    String time;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_details_search);
        binding.imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SearchActivity.class));
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();

        search = (Search) getIntent().getExtras().getSerializable("data1");


        result = new Gson().fromJson(getRecent(), SearchResult.class);
        pos = getIntent().getExtras().getInt("pos1");
        description = getIntent().getStringExtra("des");
        start = getIntent().getStringExtra("ratings");
        time = getIntent().getStringExtra("time");

        binding.tvName.setText(search.getPlaceName());
        binding.tvCountryName.setText(search.getCountryName());
        binding.tvPrice.setText(search.getPrice());
        Picasso
                .with(DetailsSearchActivity.this)
                .load(Uri.parse(search.getImageUrl()))
                .into(binding.imBg);
        binding.tvDescription.setText(description);
        binding.tvStar.setText(start);
        binding.tvTime.setText(time);
        binding.btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAddItem(search);
            }
        });
    }

    private void onClickAddItem(Search search) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef =database.getReference("booking");
        String name= String.valueOf(search.getPlaceName());
        String idUser = user.getUid();
        myRef.child(idUser).child(name).setValue(search, new DatabaseReference.CompletionListener() {
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
