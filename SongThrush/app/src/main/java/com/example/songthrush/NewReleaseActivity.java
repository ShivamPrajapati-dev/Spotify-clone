package com.example.songthrush;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewReleaseActivity extends AppCompatActivity {
    List<NewRelease> list;
    RecyclerView recyclerView;
    int x = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_release);
        list = new ArrayList<>();
        recyclerView = findViewById(R.id.newReleaseRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        RetrofitInitialize initialize = new RetrofitInitialize();
        String token = getSharedPreferences("spotify", MODE_PRIVATE).getString("token", null);
        Call<Object> call = initialize.init(token).getNewRelease("IN");
        call.enqueue(new Callback<Object>() {

            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.code() == 200) {
                    assert response.body() != null;
                    String json = new Gson().toJson(response.body());

                    JSONObject object = null;
                    try {
                        object = new JSONObject(json);
                        JSONArray items = object.getJSONObject("albums").getJSONArray("items");
                        for (int i = 0; i < items.length(); i++) {

                            String id = items.getJSONObject(i).getString("uri");
                            String name = items.getJSONObject(i).getString("name");
                            String url = items.getJSONObject(i).getJSONArray("images").getJSONObject(0).getString("url");
                            list.add(new NewRelease(url, id, name));
                            inflateRecylerView(list, items.length());
                        }
                    } catch (Exception e) {
                        Log.i("fuck", e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Toast.makeText(NewReleaseActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void inflateRecylerView(List<NewRelease> list, int length) {
        x++;


        if (x == length) {
            NewReleaseAdapter adapter = new NewReleaseAdapter(list);
            recyclerView.setAdapter(adapter);
        }
    }
}