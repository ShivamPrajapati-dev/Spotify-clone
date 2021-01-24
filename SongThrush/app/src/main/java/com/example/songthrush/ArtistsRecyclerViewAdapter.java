package com.example.songthrush;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.HashMap;
import java.util.List;

public class ArtistsRecyclerViewAdapter extends RecyclerView.Adapter<ArtistsRecyclerViewAdapter.ViewHolder> {

    private List<ArtistForRecyclerView> list;
    Context context;
    public ArtistsRecyclerViewAdapter(List<ArtistForRecyclerView> artistForRecyclerViews) {
        this.list = artistForRecyclerViews;
    }

    @NonNull
    @Override
    public ArtistsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Fresco.initialize(parent.getContext());
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.artist_recyclerview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistsRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.draweeView.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_animation));
        holder.name.setText(list.get(position).getName());
        Uri uri = Uri.parse(list.get(position).getImages()[1].getUrl());
        holder.draweeView.setImageURI(uri);
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ArtistsTracksActivity.class);
                intent.putExtra("image",list.get(position).getImages()[0].getUrl());
                intent.putExtra("name",list.get(position).getName());
                intent.putExtra("id",list.get(position).getId());
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView draweeView;
        RelativeLayout relativeLayout;
        TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            draweeView = (SimpleDraweeView)itemView.findViewById(R.id.artistImage);

            name = (TextView) itemView.findViewById(R.id.artistName);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.card);

        }
    }


}
