package com.example.nhoxb.myflicks.api;

import com.example.nhoxb.myflicks.model.NowPlaying;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by nhoxb on 10/12/2016.
 */
public interface MovieApi {
    @GET("now_playing")
    Call<NowPlaying> getNowPlaying();
}
