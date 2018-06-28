package com.example.nhoxb.myflicks.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.nhoxb.myflicks.App;
import com.example.nhoxb.myflicks.R;
import com.example.nhoxb.myflicks.data.DataManager;
import com.example.nhoxb.myflicks.data.remote.model.ListMovie;
import com.example.nhoxb.myflicks.data.remote.model.Movie;
import com.example.nhoxb.myflicks.data.remote.model.YoutubeTrailer;
import com.example.nhoxb.myflicks.data.remote.model.YoutubeVideo;
import com.example.nhoxb.myflicks.ui.detail.DetailActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final String SELECTED_ITEM_KEY = "selectedItem";
    public static final String VIDEO_ID_KEY = "videoId";

    int popularPage = 1;

    private ListView listView;
    private ProgressBar progressBar;

    private DataManager dataManager;
    private MovieAdapter mMovieAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list_item);
        progressBar = findViewById(R.id.progress_bar);
        swipeRefreshLayout = findViewById(R.id.swipeContainer);

        dataManager = App.getDataManager();

        dataManager.getNowPlaying(new Callback<ListMovie>() {
            @Override
            public void onResponse(Call<ListMovie> call, Response<ListMovie> response) {
                handleNowPlayingResponse(response);
            }

            @Override
            public void onFailure(Call<ListMovie> call, Throwable t) {
                Log.e("MOVIE", "MOVIE API CALL FAILED " + t.getMessage());
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //Get Movie id
                final Movie movie = mMovieAdapter.getItem(i);

                //Get List trailer with movieId
                dataManager.getTrailers(movie.getId(), new Callback<YoutubeTrailer>() {
                    @Override
                    public void onResponse(Call<YoutubeTrailer> call, Response<YoutubeTrailer> response) {
                        List<YoutubeVideo> mTrailers = response.body().getTrailerList();

                        if (!mTrailers.isEmpty()) {
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
                        Toast.makeText(MainActivity.this, "Get trailers failed", Toast.LENGTH_SHORT).show();
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
                dataManager.getPopular(popularPage, new Callback<ListMovie>() {
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

    private void handleNowPlayingResponse(Response<ListMovie> response) {
        mMovieAdapter = new MovieAdapter(this, response.body().getMovieList());
        listView.setAdapter(mMovieAdapter);
        progressBar.setVisibility(View.GONE);
    }
}
