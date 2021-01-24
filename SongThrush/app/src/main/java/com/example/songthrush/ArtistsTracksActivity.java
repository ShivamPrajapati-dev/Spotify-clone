package com.example.songthrush;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArtistsTracksActivity extends AppCompatActivity {


    List<Song> list;
    int x = 0;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artists_albums);
        recyclerView = findViewById(R.id.artistAlbumRecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        list = new ArrayList<>();
        String token = getSharedPreferences("spotify", MODE_PRIVATE).getString("token", null);
        assert token != null;
        String url = getIntent().getStringExtra("image");
        String id = getIntent().getStringExtra("id");
        String artistName = getIntent().getStringExtra("name");
        Fresco.initialize(getApplicationContext());
        SimpleDraweeView simpleDraweeView = findViewById(R.id.artistImage);
        Uri uri = Uri.parse(url);
        simpleDraweeView.setImageURI(uri);

        RetrofitInitialize initialize = new RetrofitInitialize();
        Call<Object> call = initialize.init(token).getArtistTopTrack(id);

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.code() == 200) {
                    assert response.body() != null;
                    String json = new Gson().toJson(response.body());

                    JSONObject object = null;
                    try {
                        object = new JSONObject(json);
                        JSONArray items = object.getJSONArray("tracks");
                        for (int i = 0; i < items.length(); i++) {

                            String id = items.getJSONObject(i).getJSONObject("album").getString("uri");
                            String name = items.getJSONObject(i).getJSONObject("album").getString("name");
                            String url = items.getJSONObject(i).getJSONObject("album").getJSONArray("images").getJSONObject(0).getString("url");

                            Song song = new Song();
                            song.setUrl(url);
                            song.setSongName(name);
                            song.setSongUri(id);
                            song.setArtistName(artistName);
                            list.add(song);
                            inflateRecyclerView(list, items.length());
                        }
                    } catch (Exception e) {
                        Log.i("qwerty", e.getMessage());
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
            Log.i("qwerty", String.valueOf(x));

            PlaylistTracksRecyclerViewAdapter adapter = new PlaylistTracksRecyclerViewAdapter(list);
            recyclerView.setAdapter(adapter);
        }

    }
}