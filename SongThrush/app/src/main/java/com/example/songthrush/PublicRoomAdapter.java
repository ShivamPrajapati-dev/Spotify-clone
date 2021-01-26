package com.example.songthrush;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PublicRoomAdapter extends RecyclerView.Adapter<PublicRoomAdapter.ViewHolder> {
    List<Rooms> list;
    Context context;
    HashMap<String, String> map;

    public PublicRoomAdapter(List<Rooms> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public PublicRoomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.public_room_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PublicRoomAdapter.ViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        Drawable drawable = context.getDrawable(R.drawable.check_button);
        assert drawable != null;
        drawable.setColorFilter(Color.parseColor(list.get(position).getColor()), PorterDuff.Mode.SRC_ATOP);
        holder.image.setImageDrawable(drawable);
        holder.initials.setText(String.valueOf(list.get(position).getName().charAt(0)));
        holder.desc.setText(list.get(position).getDesc());

        map = new HashMap<>();
        map.put("user_id", "qqqq");
        map.put("room_id", list.get(position).getName());

        holder.join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.progressBar.setVisibility(View.VISIBLE);
                holder.join.setText("");
                holder.join.setClickable(false);

                Call<Object> call = new RetrofitX().init().createSubscription(map);

                call.enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        holder.progressBar.setVisibility(View.GONE);

                        if (response.code() == 201) {
                            holder.join.setText("Joined");

                        } else {
                            holder.join.setText("join");
                            holder.join.setClickable(true);
                            Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        holder.progressBar.setVisibility(View.GONE);

                        holder.join.setText("join");
                        holder.join.setClickable(true);
                        Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, desc, initials;
        ImageView image;
        AppCompatButton join;
        ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.roomName);
            desc = itemView.findViewById(R.id.roomDesc);
            image = itemView.findViewById(R.id.image);
            initials = itemView.findViewById(R.id.initials);
            join = itemView.findViewById(R.id.join);
            progressBar = itemView.findViewById(R.id.progress);
        }
    }
}
