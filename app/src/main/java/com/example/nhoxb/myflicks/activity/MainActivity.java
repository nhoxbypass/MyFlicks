package com.example.nhoxb.myflicks.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.nhoxb.myflicks.R;
import com.example.nhoxb.myflicks.adapter.MovieAdapter;
import com.example.nhoxb.myflicks.api.MovieApi;
import com.example.nhoxb.myflicks.model.Movie;
import com.example.nhoxb.myflicks.model.ListMovie;
import com.example.nhoxb.myflicks.model.YoutubeTrailer;
import com.example.nhoxb.myflicks.model.YoutubeVideo;
import com.example.nhoxb.myflicks.utils.RetrofitUtils;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final String SELECTED_ITEM_KEY = "selectedItem";
    public static final String VIDEO_ID_KEY = "videoId";

    int popularPage = 1;

    public ListView listView;
    public ProgressBar progressBar;
    private MovieApi mMovieApi;
    private MovieAdapter mMovieAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list_item);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        mMovieApi = RetrofitUtils.get(getString(R.string.api_key)).create(MovieApi.class);


        mMovieApi.getNowPlaying().enqueue(new Callback<ListMovie>() {
            @Override
            public void onResponse(Call<ListMovie> call, Response<ListMovie> response) {
                handleNowPlayingResponse(response);
            }

            @Override
            public void onFailure(Call<ListMovie> call, Throwable t) {
                Log.e("MOVIE","MOVIE API CALL FAILED " + t.getMessage());
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //Get Movie id
                final Movie movie = mMovieAdapter.getItem(i);

                //Get List trailer with movieId
                mMovieApi.getTrailers(movie.getId()).enqueue(new Callback<YoutubeTrailer>() {
                    @Override
                    public void onResponse(Call<YoutubeTrailer> call, Response<YoutubeTrailer> response) {
                        List<YoutubeVideo> mTrailers = response.body().getTrailerList();

                        if (!mTrailers.isEmpty())
                        {
                            String videoId = mTrailers.get(0).getSource();

                            Intent intent = new Intent(getApplicationContext(), DetailActivity.class);

                            Bundle extras = new Bundle();
                            extras.putSerializable(SELECTED_ITEM_KEY, movie);
                            intent.putExtra(VIDEO_ID_KEY, videoId);
                            intent.putExtras(extras);

                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<YoutubeTrailer> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Get trailers failed",Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mMovieApi.getPopular(popularPage).enqueue(new Callback<ListMovie>() {
                    @Override
                    public void onResponse(Call<ListMovie> call, Response<ListMovie> response) {
                        handleNowPlayingResponse(response);
                        swipeRefreshLayout.setRefreshing(false);
                        popularPage++;
                    }

                    @Override
                    public void onFailure(Call<ListMovie> call, Throwable t) {

                    }
                });
            }
        });

    }

    private void handleNowPlayingResponse( Response<ListMovie> response)
    {
        mMovieAdapter = new MovieAdapter(this,response.body().getMovieList());
        listView.setAdapter(mMovieAdapter);
        progressBar.setVisibility(View.GONE);
    }
}
