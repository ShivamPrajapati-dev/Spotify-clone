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

public class NewReleaseAdapter extends RecyclerView.Adapter<NewReleaseAdapter.ViewHolder> {
    List<NewRelease> list;
    Context context;
    public NewReleaseAdapter(List<NewRelease> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public NewReleaseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        Fresco.initialize(parent.getContext());
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_release_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        Uri uri = Uri.parse(list.get(position).getUrl());
        holder.simpleDraweeView.setImageURI(uri);

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,PlayerActivity.class);
                intent.putExtra("uri",list.get(position).getId());
                intent.putExtra("image",list.get(position).getUrl());
                intent.putExtra("name",list.get(position).getName());

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
        RelativeLayout relativeLayout;
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.relative_layout);
            simpleDraweeView = itemView.findViewById(R.id.albumImage);
            name = itemView.findViewById(R.id.albumName);
        }
    }
}
