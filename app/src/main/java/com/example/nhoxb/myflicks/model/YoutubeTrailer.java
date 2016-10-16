package com.example.nhoxb.myflicks.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nhoxb on 10/16/2016.
 */
public class YoutubeTrailer {
    public List<YoutubeVideo> getTrailerList() {
        return trailerList;
    }

    @SerializedName("youtube")
    List<YoutubeVideo> trailerList;
}


