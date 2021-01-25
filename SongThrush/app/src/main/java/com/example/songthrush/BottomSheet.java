package com.example.songthrush;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomSheet extends BottomSheetDialogFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.bottom_sheet, container, false);
        TextInputEditText room_id = view.findViewById(R.id.name);
        TextInputEditText room_desc = view.findViewById(R.id.desc);
        RetrofitX retrofitX = new RetrofitX();
        HashMap<String, String> map = new HashMap<>();

        AppCompatButton done = view.findViewById(R.id.create_room);
        AppCompatButton cancel = view.findViewById(R.id.cancel);
        ProgressBar progressBar = view.findViewById(R.id.progress);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                map.put("owner", "qqqq");
                cancel.setEnabled(false);
                cancel.setTextColor(Color.GRAY);
                map.put("room_id", room_id.getText().toString().trim());
                map.put("desc", room_desc.getText().toString().trim());
                Call<Object> call = retrofitX.init().createRoom(map);
                call.enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        if (response.code() == 201) {
                            Toast.makeText(getContext(), "Room Created", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.INVISIBLE);
                            cancel.setEnabled(true);
                            cancel.setTextColor(Color.BLACK);
                            dismiss();
                        } else {
                            Toast.makeText(getContext(), "Provide complete information", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.INVISIBLE);
                            cancel.setTextColor(Color.BLACK);
                            cancel.setEnabled(true);
                        }
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        Toast.makeText(getContext(), "some error occurred", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.INVISIBLE);
                        cancel.setEnabled(true);
                        cancel.setTextColor(Color.BLACK);
                    }
                });
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }
}
