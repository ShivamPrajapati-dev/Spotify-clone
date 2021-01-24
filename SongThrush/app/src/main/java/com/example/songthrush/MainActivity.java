package com.example.songthrush;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    int x = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.artistRecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        String token = getSharedPreferences("spotify", MODE_PRIVATE).getString("token", null);

        connected(token);

    }

    private void connected(String accessToken) {
        RetrofitInitialize initialize = new RetrofitInitialize();
        SavedArtists savedArtists = new SavedArtists();
        List<ArtistForRecyclerView> list = new ArrayList<>();

        for (String id : savedArtists.getArtists()) {
            Call<Artist> call = initialize.init(accessToken).getArtist(id);

            call.enqueue(new Callback<Artist>() {
                @Override
                public void onResponse(Call<Artist> call, Response<Artist> response) {
                    if (response.code() == 200) {
                        assert response.body() != null;
                        ArtistForRecyclerView artistForRecyclerView = new ArtistForRecyclerView();
                        artistForRecyclerView.setImages(response.body().getImages());
                        artistForRecyclerView.setUri(response.body().getUri());
                        artistForRecyclerView.setName(response.body().getName());
                        artistForRecyclerView.setId(response.body().getId());
                        list.add(artistForRecyclerView);
                        inflateRecyclerView(list, savedArtists.getArtists().size());
                    }
                }

                @Override
                public void onFailure(Call<Artist> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_LONG).show();
                }
            });


        }


    }

    private void inflateRecyclerView(List<ArtistForRecyclerView> list, int size) {
        x++;
        if (x == size) {

            ArtistsRecyclerViewAdapter adapter = new ArtistsRecyclerViewAdapter(list);
            recyclerView.setAdapter(adapter);

            fetchUserData();
        }
    }

    private void fetchUserData() {

        String token = getSharedPreferences("spotify", MODE_PRIVATE).getString("token", null);
        RetrofitInitialize initialize = new RetrofitInitialize();
        assert token != null;
        Call<UserProfile> call = initialize.init(token).profile();
        call.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                if (response.code() == 200) {
                    assert response.body() != null;
                    SharedPreferences.Editor editor = getSharedPreferences("spotify", MODE_PRIVATE).edit();
                    editor.putString("userId", response.body().getId());
                    editor.apply();
                    Toast.makeText(MainActivity.this, "Fetched", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed to get user information", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();

    }


}
