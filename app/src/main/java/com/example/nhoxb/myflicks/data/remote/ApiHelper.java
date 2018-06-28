package com.example.nhoxb.myflicks.data.remote;

import com.example.nhoxb.myflicks.data.remote.model.ListMovie;
import com.example.nhoxb.myflicks.data.remote.model.YoutubeTrailer;

import retrofit2.Callback;

/**
 * Created by tom on 6/28/18.
 */
public interface ApiHelper {
    void getNowPlaying(Callback<ListMovie> callback);

    void getPopular(Integer page, Callback<ListMovie> callback);

    void getTrailers(String videoId, Callback<YoutubeTrailer> callback);
}
