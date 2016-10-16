package com.example.nhoxb.myflicks.model;

import com.example.nhoxb.myflicks.utils.Constant;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by nhoxb on 10/12/2016.
 */


public class Movie implements Serializable {

    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;
    @SerializedName("overview")
    private String description;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("backdrop_path")
    private String backdropPath;
    @SerializedName("release_date")
    private String releasePath;
    @SerializedName("vote_average")
    private float averageVote;
    @SerializedName("vote_count")
    private float voteCount;
    @SerializedName("popularity")
    private float popularity;

    public float getAverageVote() {
        return averageVote;
    }

    public String getReleasePath() {
        return releasePath;
    }


    public String getId() {
        return id;
    }

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



    public float getVoteCount() {
        return voteCount;
    }

    public float getPopularity() {
        return popularity;
    }
}
