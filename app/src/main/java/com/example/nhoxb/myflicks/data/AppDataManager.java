package com.example.nhoxb.myflicks.data;

import com.example.nhoxb.myflicks.data.remote.ApiHelper;
import com.example.nhoxb.myflicks.data.remote.AppApiHelper;
import com.example.nhoxb.myflicks.data.remote.model.ListMovie;
import com.example.nhoxb.myflicks.data.remote.model.YoutubeTrailer;

import retrofit2.Callback;

/**
 * Created by tom on 6/28/18.
 */
public class AppDataManager implements DataManager {

    private ApiHelper apiHelper;

    public AppDataManager(String apiKey) {
        apiHelper = new AppApiHelper(apiKey);
    }

    @Override
    public void getNowPlaying(Callback<ListMovie> callback) {
        apiHelper.getNowPlaying(callback);
    }

    @Override
    public void getPopular(Integer page, Callback<ListMovie> callback) {
        apiHelper.getPopular(page, callback);
    }

    @Override
    public void getTrailers(String videoId, Callback<YoutubeTrailer> callback) {
        apiHelper.getTrailers(videoId, callback);
    }
}
