package com.example.songthrush;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitX {
    private Retrofit retrofit;
    private APIInterface apiInterface;
    private String BASE_URL = "https://7a1e916388aa.ngrok.io";

    public APIInterface init() {


        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiInterface = retrofit.create(APIInterface.class);

        return apiInterface;
    }
}
