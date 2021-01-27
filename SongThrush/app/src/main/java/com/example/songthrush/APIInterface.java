package com.example.songthrush;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("/v1/me")
    Call<UserProfile> profile();

    @GET("/v1/artists/{id}")
    Call<Artist> getArtist(@Path(value = "id", encoded = true)String id);

    @GET("/v1/browse/categories")
    Call<Category> getCategories(@Query("locale") String locale);

    @GET("/v1/browse/categories/{category_id}/playlists")
    Call<Playlists> getCategoryPlaylist(@Path(value = "category_id", encoded = true) String id, @Query("country") String locale);

    @GET("/v1/playlists/{playlist_id}/tracks")
    Call<TracksByPlaylist> getTracksByPlaylists(@Path(value = "playlist_id",encoded = true)String id);

    @GET("/v1/browse/new-releases")
    Call<Object> getNewRelease(@Query("country") String locale);

    @GET("/v1/artists/{id}/top-tracks?market=IN")
    Call<Object> getArtistTopTrack(@Path(value = "id", encoded = true)String id);

    @POST("/_room/create")
    Call<Object> createRoom(@Body HashMap<String,String> data);

    @POST("/_room/read")
    Call<Object> getRoom(@Body HashMap<String,String> data);

    @POST("/_room/readall")
    Call<Object> getAllRoom(@Body HashMap<String,String> data);

    @POST("/_subscribe/create")
    Call<Object> createSubscription(@Body HashMap<String,String> data);

    @POST("/_subscribe/read")
    Call<Object> readSubscription(@Body HashMap<String,String> data);
}
