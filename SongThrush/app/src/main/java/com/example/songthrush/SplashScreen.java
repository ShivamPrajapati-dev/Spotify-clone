package com.example.songthrush;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;

public class SplashScreen extends AppCompatActivity {

    private static final int REQUEST_CODE = 1337;
    private static final String CLIENT_ID = "c3323a8a22f2446fabc8a9844032b11f";
    private static final String REDIRECT_URI = "http://example.com/callback/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        String token = getSharedPreferences("spotify", MODE_PRIVATE).getString("token", null);
        long expiry = getSharedPreferences("spotify", MODE_PRIVATE).getLong("expiry", 0);
        long saving_time = getSharedPreferences("spotify", MODE_PRIVATE).getLong("saving_time", 0);
        AuthenticationRequest.Builder builder =
                new AuthenticationRequest.Builder(CLIENT_ID, AuthenticationResponse.Type.TOKEN, REDIRECT_URI);

        builder.setScopes(new String[]{"streaming"});
        if (token == null || System.currentTimeMillis() - saving_time>=expiry) {
            AuthenticationRequest request = builder.build();

            AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);
        } else {
            Intent intent = new Intent(SplashScreen.this, SongDetailsActivity.class);

            startActivity(intent);
            finish();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {

            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, data);

            switch (response.getType()) {

                case TOKEN:
                    // Handle successful response
                    SharedPreferences.Editor editor = getSharedPreferences("spotify", MODE_PRIVATE).edit();
                    editor.putString("token", response.getAccessToken());
                    editor.putLong("expiry", response.getExpiresIn()*100);
                    editor.putLong("saving_time", System.currentTimeMillis());
                    editor.apply();
                    Toast.makeText(SplashScreen.this, "connected", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SplashScreen.this, SongDetailsActivity.class);

                    startActivity(intent);
                    finish();
                    break;

                // Auth flow returned an error
                case ERROR:
                    // Handle error response
                    Toast.makeText(SplashScreen.this, "error", Toast.LENGTH_LONG).show();
                    finish();
                    break;

                // Most likely auth flow was cancelled
                default: {
                    // Handle other cases
                    Toast.makeText(SplashScreen.this, "guess", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        }

    }

}
