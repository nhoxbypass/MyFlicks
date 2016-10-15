package com.example.nhoxb.myflicks.model;

import com.example.nhoxb.myflicks.utils.Constant;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nhoxb on 10/12/2016.
 */
public class Movie {
    @SerializedName("title")
    private String title;
    @SerializedName("overview")
    private String description;
    @SerializedName("poster_path")
    private String posterPath;

    public String getBackdropPath() {
        return Constant.IMG_BASE_URL + backdropPath;
    }

    public String getPosterPath() {
        return Constant.IMG_BASE_URL + posterPath;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    @SerializedName("backdrop_path")
    private String backdropPath;


}
