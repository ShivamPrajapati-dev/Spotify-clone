package com.example.songthrush;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.spotify.android.appremote.api.PlayerApi;
import com.spotify.android.appremote.api.SpotifyAppRemote;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaylistTracksActivity extends AppCompatActivity {

    List<Song> list;
    int x=0;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_tracks);
        recyclerView = findViewById(R.id.categoryPlaylistRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        list=new ArrayList<>();
        String token = getSharedPreferences("spotify",MODE_PRIVATE).getString("token",null);
        assert  token!=null;
        String url = getIntent().getStringExtra("imageUrl");
        String id = getIntent().getStringExtra("id");
        Fresco.initialize(getApplicationContext());
        SimpleDraweeView simpleDraweeView = findViewById(R.id.trackImage);
        Uri uri = Uri.parse(url);
        simpleDraweeView.setImageURI(uri);


        RetrofitInitialize initialize = new RetrofitInitialize();

        Call<TracksByPlaylist> call = initialize.init(token).getTracksByPlaylists(id);

        call.enqueue(new Callback<TracksByPlaylist>() {
            @Override
            public void onResponse(Call<TracksByPlaylist> call, Response<TracksByPlaylist> response) {
                if(response.code() == 200){
                    assert response.body()!=null;
                    for(Items items : response.body().getItems()){
                        Song song = new Song();
                        song.setUrl(items.getTrack().getAlbum().getImages()[1].getUrl());
                        song.setSongName(items.getTrack().getAlbum().getName());
                        song.setArtistName(items.getTrack().getAlbum().getArtists()[0].getName());
                        song.setSongUri(items.getTrack().getAlbum().getUri());
                        song.setId(items.getTrack().getAlbum().getId());
                        song.setDuration(items.getTrack().getDuration_ms());
                        list.add(song);
                        inflateRecyclerView(list,response.body().getItems().length);
                    }

                }
            }

            @Override
            public void onFailure(Call<TracksByPlaylist> call, Throwable t) {

            }
        });
    }

    private void inflateRecyclerView(List<Song> list, int length) {
        x++;
        if(x==length){
            PlaylistTracksRecyclerViewAdapter adapter = new PlaylistTracksRecyclerViewAdapter(list);
            recyclerView.setAdapter(adapter);
        }

    }
}
