package com.example.traveltheworld.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.traveltheworld.R;
import com.example.traveltheworld.adapter.HotelAdapter;
import com.example.traveltheworld.adapter.RecentAdapter;
import com.example.traveltheworld.databinding.ActivityHotelFragmentBinding;
import com.example.traveltheworld.model.Hotel;
import com.example.traveltheworld.model.RecentData;
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

public class HotelFragment extends Fragment {
    ActivityHotelFragmentBinding binding;
    HotelAdapter hotelAdapter;
    List<Hotel> hotelsList;
    private static final String JSON_URL="http://demo6990916.mockable.io/getHotelData";
    public static HotelFragment newInstance() {

        Bundle args = new Bundle();

        HotelFragment fragment = new HotelFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_hotel_fragment, container,false);
        hotelsList = new ArrayList<>();
        GetDataRecent getData = new GetDataRecent();
        getData.execute();
        return binding.getRoot();
    }
    public class GetDataRecent extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... strings) {
            String current= "";
            try {
                URL url;
                HttpURLConnection connection= null;
                try {
                    url= new URL(JSON_URL);
                    connection = (HttpURLConnection) url.openConnection();
                    InputStream is= connection.getInputStream();
                    InputStreamReader isr= new InputStreamReader(is);
                    int data = isr.read();
                    while (data!=-1){
                        current+= (char) data;
                        data= isr.read();
                    }
                    return current;
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (connection !=null){
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
                JSONArray jsonArray= json.getJSONArray("hotels");
                for(int i=0; i<jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Hotel hotel = new Hotel();
                    hotel.setPlaceName(jsonObject.getString("placeName"));
                    hotel.setRatings(jsonObject.getString("ratings"));
                    hotel.setCountryName(jsonObject.getString("countryName"));
                    hotel.setImageUrl(jsonObject.getString("imageUrl"));
                    hotel.setDescription(jsonObject.getString("description"));
                    hotel.setTime(jsonObject.getString("time"));
                    hotel.setPrice(jsonObject.getString("price"));
                    hotelsList.add(hotel);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setRecentRecycler(hotelsList);
        }
    }
    private void setRecentRecycler(List<Hotel> hotelsList){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        binding.hotelList.setLayoutManager(layoutManager);
        hotelAdapter = new HotelAdapter(getContext(),hotelsList);
        binding.hotelList.setAdapter(hotelAdapter);
    }
}