package com.example.traveltheworld.fragment;

import static java.util.concurrent.TimeUnit.SECONDS;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.traveltheworld.Activityes.Profile;
import com.example.traveltheworld.Activityes.SearchActivity;
import com.example.traveltheworld.R;
import com.example.traveltheworld.adapter.RecentAdapter;
import com.example.traveltheworld.adapter.TopPlacesAdapter;
import com.example.traveltheworld.databinding.ActivityHomeFragmentBinding;
import com.example.traveltheworld.model.RecentData;
import com.example.traveltheworld.model.TopPlacesData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;

public class HomeFragment extends Fragment {
    ActivityHomeFragmentBinding binding;
    RecentAdapter recentAdapter;
    TopPlacesAdapter topPlacesAdapter;
    List<RecentData> recentDataList;
    List<TopPlacesData> topPlacesDataList;
    private static final String JSON_URL = "http://demo6990916.mockable.io/getRecentData";
    private static final String JSON_URL1 = "http://demo6990916.mockable.io/getTopPlaceData";


    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_home_fragment, container, false);


        recentDataList = new ArrayList<>();

        GetDataRecent getData = new GetDataRecent();
        getData.execute();

        setRecentRecycler(recentDataList);


        topPlacesDataList = new ArrayList<>();
        GetDataTopPlace getDataTopPlace = new GetDataTopPlace();
        getDataTopPlace.execute();
        setTopPlaceRecycler(topPlacesDataList);
        binding.imUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Profile.class);
                startActivity(intent);
            }
        });
        showProfile();
        binding.etSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                startActivity(intent);
            }
        });
        return binding.getRoot();
    }

    public class GetDataRecent extends AsyncTask<String, String, String> {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().readTimeout(1, SECONDS).connectTimeout(1, SECONDS).build();

        @Override
        protected String doInBackground(String... strings) {
            String current = "";
            try {
                URL url;
                HttpURLConnection connection = null;
                try {
                    url = new URL(JSON_URL);
                    connection = (HttpURLConnection) url.openConnection();
                    InputStream is = connection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    int data = isr.read();
                    while (data != -1) {
                        current += (char) data;
                        data = isr.read();
                    }
                    return current;
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return current;
        }

        @Override
        protected void onPostExecute(String s) {
            RecentData recentData = null;
            try {
                JSONObject json = new JSONObject(s);
                JSONArray jsonArray = json.getJSONArray("place");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    recentData = new RecentData();
                    recentData.setPlaceName(jsonObject.getString("placeName"));
                    recentData.setCountryName(jsonObject.getString("countryName"));
                    recentData.setPrice(jsonObject.getString("price"));
                    recentData.setImageUrl(jsonObject.getString("imageUrl"));
                    recentData.setRatings(jsonObject.getString("ratings"));
                    recentData.setDescription(jsonObject.getString("description"));
                    recentData.setTime(jsonObject.getString("time"));
                    recentDataList.add(recentData);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setRecentRecycler(recentDataList);
        }
    }

    public class GetDataTopPlace extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            String current = "";
            try {
                URL url;
                HttpURLConnection connection = null;
                try {
                    url = new URL(JSON_URL1);
                    connection = (HttpURLConnection) url.openConnection();
                    InputStream is = connection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    int data = isr.read();
                    while (data != -1) {
                        current += (char) data;
                        data = isr.read();
                    }
                    return current;
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return current;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject json = new JSONObject(s);
                JSONArray jsonArray = json.getJSONArray("place");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    TopPlacesData topPlacesData = new TopPlacesData();
                    topPlacesData.setPlaceName(jsonObject.getString("placeName"));
                    topPlacesData.setCountryName(jsonObject.getString("countryName"));
                    topPlacesData.setPrice(jsonObject.getString("price"));
                    topPlacesData.setImageUrl(jsonObject.getString("imageUrl"));
                    topPlacesData.setRatings(jsonObject.getString("ratings"));
                    topPlacesData.setDescription(jsonObject.getString("description"));
                    topPlacesData.setTime(jsonObject.getString("time"));
                    topPlacesDataList.add(topPlacesData);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setTopPlaceRecycler(topPlacesDataList);
        }
    }

    private void setRecentRecycler(List<RecentData> recentDataList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        binding.rcRecent.setLayoutManager(layoutManager);
        recentAdapter = new RecentAdapter(getContext(), recentDataList);
        binding.rcRecent.setAdapter(recentAdapter);
    }

    private void setTopPlaceRecycler(List<TopPlacesData> topPlacesDataList) {
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        binding.rcTopPlaces.setLayoutManager(layoutManager1);
        topPlacesAdapter = new TopPlacesAdapter(getContext(), topPlacesDataList);
        binding.rcTopPlaces.setAdapter(topPlacesAdapter);
    }

    @SuppressLint("SetTextI18n")
    private void showProfile() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }
        String name = user.getDisplayName();
        if(name == null){
            binding.tvNext.setText("Người dùng");
        }else{
            binding.tvNext.setText(name);
        }
        Uri photoUrl = user.getPhotoUrl();
        Glide.with(this).load(photoUrl).error(R.drawable.logo_user).into(binding.imUser);
    }

}