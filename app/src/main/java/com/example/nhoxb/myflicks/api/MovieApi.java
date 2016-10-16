package com.example.nhoxb.myflicks.api;

import com.example.nhoxb.myflicks.model.ListMovie;
import com.example.nhoxb.myflicks.model.YoutubeTrailer;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by nhoxb on 10/12/2016.
 */
public interface MovieApi {
    @GET("now_playing")
    Call<ListMovie> getNowPlaying();

    @GET("popular")
    Call<ListMovie> getPopular(@Query("page") Integer page);

    @GET("{videoId}/trailers")
    Call<YoutubeTrailer> getTrailers(@Path("videoId") String videoId);
}
