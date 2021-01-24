package com.example.songthrush;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class CategoryPlaylistRecyclerViewAdapter extends RecyclerView.Adapter<CategoryPlaylistRecyclerViewAdapter.ViewHolder> {

    List<CategoryPlaylist> list;
    Context context;
    public CategoryPlaylistRecyclerViewAdapter(List<CategoryPlaylist> list){
        this.list = list;
    }

    @NonNull
    @Override
    public CategoryPlaylistRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        Fresco.initialize(parent.getContext());
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_playlist_recyclerview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryPlaylistRecyclerViewAdapter.ViewHolder holder, int position) {

        holder.name.setText(list.get(position).getName());
        Uri uri = Uri.parse(list.get(position).getImageUrl());
        holder.simpleDraweeView.setImageURI(uri);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.description.setText(Html.fromHtml(list.get(position).getDescription(),Html.FROM_HTML_MODE_COMPACT));
        }else{
            holder.description.setText(Html.fromHtml(list.get(position).getDescription()));

        }

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,PlaylistTracksActivity.class);
                intent.putExtra("imageUrl",list.get(position).getImageUrl());
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
        SimpleDraweeView simpleDraweeView;
        TextView name, description;
        RelativeLayout relativeLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            simpleDraweeView = itemView.findViewById(R.id.playlistImage);
            name = itemView.findViewById(R.id.playlistName);
            description = itemView.findViewById(R.id.playlistDescription);
            relativeLayout = itemView.findViewById(R.id.categoryPlaylistLayout);
        }
    }
}
