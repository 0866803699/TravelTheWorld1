package com.example.traveltheworld.Activityes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.traveltheworld.R;
import com.example.traveltheworld.databinding.ActivityMainBinding;
import com.example.traveltheworld.fragment.BookingFragment;
import com.example.traveltheworld.fragment.ChatFragment;
import com.example.traveltheworld.fragment.HomeFragment;
import com.example.traveltheworld.fragment.HotelFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        onFragment(HomeFragment.newInstance());
        binding.bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_home:
                        onFragment(HomeFragment.newInstance());
                        break;
                    case R.id.action_booking:
                        onFragment(BookingFragment.newInstance());
                        break;
                    case R.id.action_hotel:
                        onFragment(HotelFragment.newInstance());
                        break;
                    case R.id.action_chat:
                        onFragment(ChatFragment.newInstance());
                        break;
                }
                return true;
            }
        });
    }
    private void onFragment(Fragment fragment){
        try {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragContainer,fragment).commit();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}