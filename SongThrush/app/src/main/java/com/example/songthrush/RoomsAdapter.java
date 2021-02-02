package com.example.songthrush;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.List;

public class RoomsAdapter extends RecyclerView.Adapter<RoomsAdapter.ViewHolder> {
    List<Rooms> list;
    Context context;

    public RoomsAdapter(List<Rooms> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public RoomsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        Fresco.initialize(parent.getContext());
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rooms_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomsAdapter.ViewHolder holder, int position) {

        holder.name.setText(list.get(position).getName());
        Drawable drawable = context.getDrawable(R.drawable.check_button);
        assert drawable != null;
        drawable.setColorFilter(Color.parseColor(list.get(position).getColor()), PorterDuff.Mode.SRC_ATOP);
        holder.draweeView.setImageDrawable(drawable);
        holder.initials.setText(String.valueOf(list.get(position).getName().charAt(0)));
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RoomSongActivity.class);
                intent.putExtra("initials",String.valueOf(list.get(position).getName().charAt(0)));
                intent.putExtra("color", list.get(position).getColor());
                intent.putExtra("room_id",list.get(position).getName());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView draweeView;
        TextView name,initials;
        RelativeLayout relativeLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            draweeView = itemView.findViewById(R.id.artistImage);
            name = itemView.findViewById(R.id.artistName);
            initials = itemView.findViewById(R.id.initials);
            relativeLayout = itemView.findViewById(R.id.categoryLayout);
        }
    }
}
