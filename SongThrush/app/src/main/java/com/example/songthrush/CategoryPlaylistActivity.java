package com.example.songthrush;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryPlaylistActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<CategoryPlaylist> list;
    int x=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_playlist);

        list = new ArrayList<>();
        recyclerView = findViewById(R.id.categoryPlaylistRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        String id = getIntent().getStringExtra("playlistId");
        String token = getSharedPreferences("spotify", MODE_PRIVATE).getString("token",null);
        assert token!=null;
        RetrofitInitialize initialize = new RetrofitInitialize();
        Call<Playlists> call = initialize.init(token).getCategoryPlaylist(id,"IN");

        call.enqueue(new Callback<Playlists>() {
            @Override
            public void onResponse(Call<Playlists> call, Response<Playlists> response) {
                if(response.code() == 200){
                    assert response.body()!=null;
                    Log.i("aaaaa",new Gson().toJson(response.body()));

                    for(Items item : response.body().getPlaylists().getItems()){
                        CategoryPlaylist categoryPlaylist = new CategoryPlaylist();
                        categoryPlaylist.setImageUrl(item.getImages()[0].getUrl());
                        categoryPlaylist.setId(item.getId());
                        categoryPlaylist.setDescription(item.getDescription());
                        categoryPlaylist.setName(item.getName());
                        list.add(categoryPlaylist);
                        inflateRecylerView(list,response.body().getPlaylists().getItems().length);

                    }

                }
            }

            @Override
            public void onFailure(Call<Playlists> call, Throwable t) {

            }
        });

    }

    private void inflateRecylerView(List<CategoryPlaylist> list, int length) {
        x++;
        if(x == length){
            CategoryPlaylistRecyclerViewAdapter adapter = new CategoryPlaylistRecyclerViewAdapter(list);
            recyclerView.setAdapter(adapter);
        }
    }
}
