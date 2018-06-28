package com.example.nhoxb.myflicks.data.remote.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nhoxb on 10/16/2016.
 */
public class YoutubeVideo {
    @SerializedName("source")
    private String source;

    public String getSource() {
        return source;
    }
}