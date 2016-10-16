package com.example.nhoxb.myflicks.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nhoxb on 10/12/2016.
 */
public class ListMovie {
    public List<Movie> getMovieList() {
        return movieList;
    }

    @SerializedName("results")
    List<Movie> movieList;


}
