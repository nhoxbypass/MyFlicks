package com.example.nhoxb.myflicks.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.nhoxb.myflicks.R;
import com.example.nhoxb.myflicks.adapter.MovieAdapter;
import com.example.nhoxb.myflicks.api.MovieApi;
import com.example.nhoxb.myflicks.model.Movie;
import com.example.nhoxb.myflicks.model.NowPlaying;
import com.example.nhoxb.myflicks.utils.RetrofitUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public ListView listView;
    public ProgressBar progressBar;
    private MovieApi mMovieApi;
    private MovieAdapter mMovieAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list_item);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        mMovieApi = RetrofitUtils.get(getString(R.string.api_key)).create(MovieApi.class);


        mMovieApi.getNowPlaying().enqueue(new Callback<NowPlaying>() {
            @Override
            public void onResponse(Call<NowPlaying> call, Response<NowPlaying> response) {
                handleResponse(response);
            }

            @Override
            public void onFailure(Call<NowPlaying> call, Throwable t) {
                Log.e("MOVIE","MOVIE API CALL FAILED " + t.getMessage());
            }
        });
    }

    private void handleResponse( Response<NowPlaying> response)
    {
        mMovieAdapter = new MovieAdapter(this,response.body().getMovieList());
        listView.setAdapter(mMovieAdapter);
        progressBar.setVisibility(View.GONE);
    }
}
