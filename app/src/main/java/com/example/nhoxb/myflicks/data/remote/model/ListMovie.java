package com.example.nhoxb.myflicks.data.remote.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nhoxb on 10/12/2016.
 */
public class ListMovie {
    @SerializedName("results")
    List<Movie> movieList;

    public List<Movie> getMovieList() {
        return movieList;
    }


}
