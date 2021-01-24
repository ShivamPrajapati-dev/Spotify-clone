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

public class CategoriesRecyclerViewAdapter extends RecyclerView.Adapter<CategoriesRecyclerViewAdapter.ViewHolder> {

    List<CategoriesForRecyclerView> list;
    Context context;
    public CategoriesRecyclerViewAdapter(List<CategoriesForRecyclerView> list){
        this.list = list;

    }

    @NonNull
    @Override
    public CategoriesRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        Fresco.initialize(parent.getContext());
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_recyclerview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesRecyclerViewAdapter.ViewHolder holder, int position) {

        holder.name.setText(list.get(position).getName());
        Uri uri = Uri.parse(list.get(position).getUrl());
        holder.draweeView.setImageURI(uri);

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,CategoryPlaylistActivity.class);
                intent.putExtra("playlistId",list.get(position).getId());
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
        TextView name;
        RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            draweeView = itemView.findViewById(R.id.artistImage);
            name = itemView.findViewById(R.id.artistName);
            relativeLayout = itemView.findViewById(R.id.categoryLayout);
        }
    }
}
