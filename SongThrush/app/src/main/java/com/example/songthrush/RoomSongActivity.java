package com.example.songthrush;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomSongActivity extends AppCompatActivity {

    ImageView image;
    TextView initials;
    HashMap<String, String> map;
    List<Song> list;
    int x = 0;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_song);
        image = findViewById(R.id.image);
        initials = findViewById(R.id.initials);
        map = new HashMap<>();
        list = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        Intent intent = getIntent();
        String color = intent.getStringExtra("color");
        String inti = intent.getStringExtra("initials");
        String id = intent.getStringExtra("room_id");
        map.put("id", id);
        image.setBackgroundColor(Color.parseColor(color));
        initials.setText(inti);

        RetrofitX retrofitX = new RetrofitX();
        Call<Object> call = retrofitX.init().readMusic(map);

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.code() == 200) {
                    assert response.body() != null;
                    String json = new Gson().toJson(response.body());

                    try {
                        JSONArray items = new JSONArray(json);
                        for (int i = 0; i < items.length(); i++) {

                            String id = items.getJSONObject(i).getString("music_id");
                            String name = items.getJSONObject(i).getString("artist");
                            String url = items.getJSONObject(i).getString("img");
                            Song song = new Song();
                            song.setArtistName(name);
                            song.setSongUri(id);
                            song.setUrl(url);
                            list.add(song);
                            inflateRecyclerView(list, items.length());
                        }
                    } catch (Exception e) {
                        Log.i("fuck", e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }

    private void inflateRecyclerView(List<Song> list, int length) {
        x++;
        if (x == length) {
            PlaylistTracksRecyclerViewAdapter adapter = new PlaylistTracksRecyclerViewAdapter(list);
            recyclerView.setAdapter(adapter);
        }

    }
}