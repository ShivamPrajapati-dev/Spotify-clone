package com.example.songthrush;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MySpaceActivity extends AppCompatActivity {
    ExtendedFloatingActionButton fab;
    BottomSheet bottomSheet;
    RecyclerView myRoomsRV, subsRV, publicRoomRV;
    SwipeRefreshLayout swipeRefreshLayout;
    List<Rooms> list, listall, listSubs;
    int x = 0, y = 0, z = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_space);
        fab = findViewById(R.id.fab);
        myRoomsRV = findViewById(R.id.myRoomsRecyclerView);
        subsRV = findViewById(R.id.mySubsRoomsRecyclerView);
        publicRoomRV = findViewById(R.id.publicRoomsRecyclerView);

        myRoomsRV.setHasFixedSize(true);
        subsRV.setHasFixedSize(true);
        publicRoomRV.setHasFixedSize(true);

        myRoomsRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        subsRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        publicRoomRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        list = new ArrayList<>();
        listall = new ArrayList<>();
        listSubs = new ArrayList<>();

        swipeRefreshLayout = findViewById(R.id.swipe);
        init();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                init();
                swipeRefreshLayout.setRefreshing(false);
            }
        });


    }

    private void init() {
        x = 0;
        y = 0;
        z = 0;
        list.clear();
        listSubs.clear();
        listall.clear();


        bottomSheet = new BottomSheet();
        bottomSheet.setCancelable(false);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheet.show(getSupportFragmentManager(), "tag");
            }
        });
        RetrofitX retrofitX = new RetrofitX();
        HashMap<String, String> map = new HashMap<>();
        map.put("id", "qqqq");

        Call<Object> call = retrofitX.init().getRoom(map);
        Call<Object> callAll = retrofitX.init().getAllRoom(map);
        Call<Object> callSubs = retrofitX.init().readSubscription(map);

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.code() == 200) {
                    String json = new Gson().toJson(response.body());
                    try {
                        JSONArray array = new JSONArray(json);
                        for (int i = 0; i < array.length(); i++) {
                            String color = array.getJSONObject(i).getString("color");
                            String name = array.getJSONObject(i).getString("room_id");
                            String desc = array.getJSONObject(i).getString("desc");
                            Rooms rooms = new Rooms(name, desc, color);
                            list.add(rooms);
                            inflateMyRoomsRecyclerView(list, array.length());
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(MySpaceActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Toast.makeText(MySpaceActivity.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });

        callAll.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.code() == 200) {
                    String json = new Gson().toJson(response.body());
                    try {
                        JSONArray array = new JSONArray(json);
                        for (int i = 0; i < array.length(); i++) {
                            String color = array.getJSONObject(i).getString("color");
                            String name = array.getJSONObject(i).getString("room_id");
                            String desc = array.getJSONObject(i).getString("desc");
                            Rooms rooms = new Rooms(name, desc, color);
                            listall.add(rooms);
                            inflatePubRoomsRecyclerView(listall, array.length());
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(MySpaceActivity.this, new Gson().toJson(response.errorBody()), Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Toast.makeText(MySpaceActivity.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }
        });

        callSubs.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.code() == 200) {
                    String json = new Gson().toJson(response.body());
                    try {
                        JSONArray array = new JSONArray(json);
                        for (int i = 0; i < array.length(); i++) {
                            String color = array.getJSONObject(i).getString("color");
                            String name = array.getJSONObject(i).getString("room_id");
                            String desc = array.getJSONObject(i).getString("desc");
                            Rooms rooms = new Rooms(name, desc, color);
                            listSubs.add(rooms);
                            inflateSubsRoomRecyclerView(listSubs, array.length());
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(MySpaceActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Toast.makeText(MySpaceActivity.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    private void inflateMyRoomsRecyclerView(List<Rooms> list, int length) {
        x++;
        if (x == length) {

            RoomsAdapter roomsAdapter = new RoomsAdapter(list);
            roomsAdapter.notifyDataSetChanged();
            myRoomsRV.setAdapter(roomsAdapter);

        }

    }

    private void inflatePubRoomsRecyclerView(List<Rooms> list, int length) {
        y++;
        if (y == length) {

            PublicRoomAdapter publicRoomAdapter = new PublicRoomAdapter(list);
            publicRoomAdapter.notifyDataSetChanged();
            publicRoomRV.setAdapter(publicRoomAdapter);

        }

    }

    private void inflateSubsRoomRecyclerView(List<Rooms> list, int length) {
        z++;
        if (z == length) {


            RoomsAdapter roomsAdapter = new RoomsAdapter(list);
            roomsAdapter.notifyDataSetChanged();
            subsRV.setAdapter(roomsAdapter);

        }

    }

}