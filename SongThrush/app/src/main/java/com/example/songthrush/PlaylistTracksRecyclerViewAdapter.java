package com.example.songthrush;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.material.snackbar.Snackbar;
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.client.CallResult;
import com.spotify.protocol.types.PlayerState;

import java.util.List;

public class PlaylistTracksRecyclerViewAdapter extends RecyclerView.Adapter<PlaylistTracksRecyclerViewAdapter.ViewHolder> {

    List<Song> list;
    Context context;
    ViewGroup viewGroup;
    private static final String CLIENT_ID = "c3323a8a22f2446fabc8a9844032b11f";
    private static final String REDIRECT_URI = "http://example.com/callback/";
    private SpotifyAppRemote mSpotifyAppRemote;

    public PlaylistTracksRecyclerViewAdapter(List<Song> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public PlaylistTracksRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        Fresco.initialize(parent.getContext());
        viewGroup = parent;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tracks_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistTracksRecyclerViewAdapter.ViewHolder holder, int position) {

        Uri uri = Uri.parse(list.get(position).getUrl());
        holder.simpleDraweeView.setImageURI(uri);
        holder.songName.setText(list.get(position).getSongName());
        holder.artistName.setText(list.get(position).getArtistName());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//               Intent intent = new Intent(context,PlayerActivity.class);
//               intent.putExtra("uri",list.get(position).getSongUri());
//               intent.putExtra("image",list.get(position).getUrl());
//               intent.putExtra("name",list.get(position).getSongName());
//               intent.putExtra("artist",list.get(position).getArtistName());
//               intent.putExtra("id",list.get(position).getId());
//               intent.putExtra("time",list.get(position).getDuration());
//               context.startActivity(intent);
                Snackbar snackbar = Snackbar.make(v, "", Snackbar.LENGTH_INDEFINITE);
                View view = LayoutInflater.from(context).inflate(R.layout.snackbar_player, null, false);
                Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
                snackbarLayout.setPadding(0, 0, 0, 0);
                snackbarLayout.addView(view, 0);

                Uri uriImage = Uri.parse(list.get(position).getUrl());
                SimpleDraweeView simpleDraweeView = view.findViewById(R.id.songImage);
                simpleDraweeView.setImageURI(uriImage);
                ImageView play = view.findViewById(R.id.play);
                TextView songName = view.findViewById(R.id.songName);
                songName.setText(list.get(position).getSongName());

                ConnectionParams connectionParams =
                        new ConnectionParams.Builder(CLIENT_ID)
                                .setRedirectUri(REDIRECT_URI)
                                .showAuthView(true)
                                .build();

                SpotifyAppRemote.connect(context, connectionParams,
                        new Connector.ConnectionListener() {

                            @Override
                            public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                                mSpotifyAppRemote = spotifyAppRemote;
                                Log.d("MainActivity", "Connected! Yay!");

                                // Now you can start interacting with App Remote
                                mSpotifyAppRemote.getPlayerApi().play(list.get(position).getSongUri());
                                play.setBackground(context.getDrawable(R.drawable.ic_pause_circle_filled_black_24dp));
                            }

                            @Override
                            public void onFailure(Throwable throwable) {
                                Log.e("MainActivity", throwable.getMessage(), throwable);

                                // Something went wrong when attempting to connect! Handle errors here
                            }
                        });

                play.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CallResult<PlayerState> callResult = mSpotifyAppRemote.getPlayerApi().getPlayerState();

                        callResult.setResultCallback(new CallResult.ResultCallback<PlayerState>() {
                            @Override
                            public void onResult(PlayerState playerState) {
                                if (!playerState.isPaused) {
                                    mSpotifyAppRemote.getPlayerApi().pause();
                                    play.setBackground(context.getDrawable(R.drawable.ic_play_circle_filled_black_24dp));

                                } else {
                                    mSpotifyAppRemote.getPlayerApi().resume();
                                    play.setBackground(context.getDrawable(R.drawable.ic_pause_circle_filled_black_24dp));

                                }
                                Log.i("poss", String.valueOf(playerState.playbackPosition));

                            }
                        });
                    }
                });

                snackbar.show();
            }
        });

        holder.relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                BottomSheetAddSong bottomSheetAddSong = new BottomSheetAddSong();
                bottomSheetAddSong.show(((FragmentActivity) context).getSupportFragmentManager(), "xxxx");
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView simpleDraweeView;
        TextView songName, artistName;
        RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            simpleDraweeView = itemView.findViewById(R.id.songImage);
            songName = itemView.findViewById(R.id.songName);
            artistName = itemView.findViewById(R.id.songArtistName);
            relativeLayout = itemView.findViewById(R.id.trackLayout);


        }
    }
}
