package com.example.songthrush;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class PlaylistTracksRecyclerViewAdapter extends RecyclerView.Adapter<PlaylistTracksRecyclerViewAdapter.ViewHolder> {

    List<Song> list;
    Context context;
    public PlaylistTracksRecyclerViewAdapter(List<Song> list){
        this.list = list;
    }

    @NonNull
    @Override
    public PlaylistTracksRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        Fresco.initialize(parent.getContext());
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tracks_recyclerview,parent,false);
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
               Intent intent = new Intent(context,PlayerActivity.class);
               intent.putExtra("uri",list.get(position).getSongUri());
               intent.putExtra("image",list.get(position).getUrl());
               intent.putExtra("name",list.get(position).getSongName());
               intent.putExtra("artist",list.get(position).getArtistName());
               intent.putExtra("id",list.get(position).getId());
               intent.putExtra("time",list.get(position).getDuration());
               context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView simpleDraweeView;
        TextView songName,artistName;
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
