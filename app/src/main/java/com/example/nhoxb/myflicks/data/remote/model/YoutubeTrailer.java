package com.example.nhoxb.myflicks.data.remote.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nhoxb on 10/16/2016.
 */
public class YoutubeTrailer {
    @SerializedName("youtube")
    List<YoutubeVideo> trailerList;

    public List<YoutubeVideo> getTrailerList() {
        return trailerList;
    }
}


