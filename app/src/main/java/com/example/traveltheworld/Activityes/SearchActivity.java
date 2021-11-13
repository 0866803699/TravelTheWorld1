package com.example.traveltheworld.Activityes;

import static java.util.concurrent.TimeUnit.SECONDS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.example.traveltheworld.R;
import com.example.traveltheworld.adapter.RecentAdapter;
import com.example.traveltheworld.adapter.SearchAdapter;
import com.example.traveltheworld.databinding.ActivitySearchBinding;
import com.example.traveltheworld.model.RecentData;
import com.example.traveltheworld.model.Search;

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

public class SearchActivity extends AppCompatActivity {
    ActivitySearchBinding binding;
    SearchAdapter searchAdapter;
    List<Search> searchList;
    private static final  String JSON_URL = "http://demo6990916.mockable.io/searchData";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_search);
        searchList = new ArrayList<>();
        GetDataSearch searchData = new GetDataSearch();
        searchData.execute();
        binding.imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
        setSearchRecycler(searchList);
        binding.etSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    public class GetDataSearch extends AsyncTask<String, String, String> {
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
            Search search = null;
            try {
                JSONObject json = new JSONObject(s);
                JSONArray jsonArray = json.getJSONArray("place");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    search = new Search();
                    search.setPlaceName(jsonObject.getString("placeName"));
                    search.setCountryName(jsonObject.getString("countryName"));
                    search.setPrice(jsonObject.getString("price"));
                    search.setImageUrl(jsonObject.getString("imageUrl"));
                    search.setRatings(jsonObject.getString("ratings"));
                    search.setDescription(jsonObject.getString("description"));
                    search.setTime(jsonObject.getString("time"));
                    searchList.add(search);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setSearchRecycler(searchList);
        }
    }

    private void setSearchRecycler(List<Search> searchList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getBaseContext(), RecyclerView.VERTICAL, false);
        binding.rcSearch.setLayoutManager(layoutManager);
        searchAdapter = new SearchAdapter(getBaseContext(), searchList);
        binding.rcSearch.setAdapter(searchAdapter);
    }
}