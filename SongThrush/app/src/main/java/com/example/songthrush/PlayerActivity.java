package com.example.songthrush;

import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.client.CallResult;
import com.spotify.protocol.types.PlayerState;


public class PlayerActivity extends AppCompatActivity {
    private static final String CLIENT_ID = "c3323a8a22f2446fabc8a9844032b11f";
    private static final String REDIRECT_URI = "http://example.com/callback/";
    private SpotifyAppRemote mSpotifyAppRemote;
    String uri;
    ImageView play;
    CountDownTimer timer;
    SeekBar seekBar;
    int timePassed = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        Fresco.initialize(getApplicationContext());
        Uri uriImage = Uri.parse(getIntent().getStringExtra("image"));
        SimpleDraweeView simpleDraweeView = findViewById(R.id.imageSong);
        simpleDraweeView.setImageURI(uriImage);
        TextView songName = findViewById(R.id.songName);
        songName.setText(getIntent().getStringExtra("name"));
        TextView artist = findViewById(R.id.artistName);
        artist.setText(getIntent().getStringExtra("artist"));
        uri = getIntent().getStringExtra("uri");
        int time = getIntent().getIntExtra("time", 0);

        seekBar = findViewById(R.id.seekBar);
        seekBar.setMax(time);

        play = findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallResult<PlayerState> callResult = mSpotifyAppRemote.getPlayerApi().getPlayerState();

                callResult.setResultCallback(new CallResult.ResultCallback<PlayerState>() {
                    @Override
                    public void onResult(PlayerState playerState) {
                        if (!playerState.isPaused) {
                            mSpotifyAppRemote.getPlayerApi().pause();
                            play.setBackground(getDrawable(R.drawable.ic_play_circle_filled_black_24dp));
                            startTimer(timePassed);

                        } else {
                            mSpotifyAppRemote.getPlayerApi().resume();
                            play.setBackground(getDrawable(R.drawable.ic_pause_circle_filled_black_24dp));

                            // pauseTimer();
                        }
                        Log.i("poss", String.valueOf(playerState.playbackPosition));

                    }
                });
            }
        });

        ConnectionParams connectionParams =
                new ConnectionParams.Builder(CLIENT_ID)
                        .setRedirectUri(REDIRECT_URI)
                        .showAuthView(true)
                        .build();

        SpotifyAppRemote.connect(this, connectionParams,
                new Connector.ConnectionListener() {

                    @Override
                    public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                        mSpotifyAppRemote = spotifyAppRemote;
                        Log.d("MainActivity", "Connected! Yay!");

                        // Now you can start interacting with App Remote
                        connected();
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.e("MainActivity", throwable.getMessage(), throwable);

                        // Something went wrong when attempting to connect! Handle errors here
                    }
                });


    }

    public void startTimer(int time) {
        timer = new CountDownTimer(time, 1) {

            @Override
            public void onTick(long millisUntilFinished) {
                seekBar.setProgress((int) (time - millisUntilFinished));
                timePassed = (int) (time - millisUntilFinished);
            }

            @Override
            public void onFinish() {

            }
        };
        timer.start();
    }

    public void pauseTimer() {
        timer.cancel();
    }


    private void connected() {
        mSpotifyAppRemote.getPlayerApi().play(uri);
        play.setBackground(getDrawable(R.drawable.ic_pause_circle_filled_black_24dp));
        startTimer(timePassed);
    }
}
