package com.example.songthrush;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SongDetailsActivity extends AppCompatActivity {

    int x = 0;
    RecyclerView recyclerView;
    MaterialCardView cardView,artistCardView, mySpaceCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<CategoriesForRecyclerView> categoriesList = new ArrayList<>();
        setContentView(R.layout.activity_song_details);
        recyclerView = findViewById(R.id.catRecyclerView);
        recyclerView.setHasFixedSize(true);
        cardView = findViewById(R.id.new_release);
        artistCardView = findViewById(R.id.artists);
        mySpaceCardView = findViewById(R.id.my_space);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        String token = getSharedPreferences("spotify", MODE_PRIVATE).getString("token", null);
        assert token != null;
        RetrofitInitialize initialize = new RetrofitInitialize();
        Call<Category> call = initialize.init(token).getCategories("en_IN");
        call.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                if (response.code() == 200) {
                    assert response.body() != null;
                    for (Items items : response.body().getCategories().getItems()) {
                        Log.i("aaaax", items.getName());

                        CategoriesForRecyclerView categoriesForRecyclerView = new CategoriesForRecyclerView();
                        categoriesForRecyclerView.setId(items.getId());
                        categoriesForRecyclerView.setUrl(items.getIcons()[0].getUrl());
                        categoriesForRecyclerView.setName(items.getName());
                        categoriesList.add(categoriesForRecyclerView);
                        inflateRecyclerView(categoriesList, response.body().getCategories().getItems().length);
                    }
                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {

            }
        });

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SongDetailsActivity.this, NewReleaseActivity.class));
            }
        });

        artistCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SongDetailsActivity.this, MainActivity.class));

            }
        });
        mySpaceCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SongDetailsActivity.this, MySpaceActivity.class));

            }
        });

    }


    private void inflateRecyclerView(List<CategoriesForRecyclerView> categories, int length) {
        x++;

        if (x == length) {
            CategoriesRecyclerViewAdapter adapter = new CategoriesRecyclerViewAdapter(categories);
            recyclerView.setAdapter(adapter);
        }
    }
}
