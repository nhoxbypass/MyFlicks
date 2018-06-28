package com.example.nhoxb.myflicks.data.remote;

import com.example.nhoxb.myflicks.data.remote.model.ListMovie;
import com.example.nhoxb.myflicks.data.remote.model.YoutubeTrailer;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by tom on 6/28/18.
 */
public class AppApiHelper implements ApiHelper {
    private MovieApi mMovieApi;

    public AppApiHelper(String apiKey) {
        mMovieApi = getRetrofit(apiKey).create(MovieApi.class);
    }

    @Override
    public void getNowPlaying(Callback<ListMovie> callback) {
        mMovieApi.getNowPlaying().enqueue(callback);
    }

    @Override
    public void getPopular(Integer page, Callback<ListMovie> callback) {
        mMovieApi.getPopular(page).enqueue(callback);
    }

    @Override
    public void getTrailers(String videoId, Callback<YoutubeTrailer> callback) {
        mMovieApi.getTrailers(videoId).enqueue(callback);
    }

    private Retrofit getRetrofit(String apiKey) {
        return new Retrofit.Builder()
                .baseUrl(ApiEndPoint.BASE_URL)
                .client(client(apiKey))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private OkHttpClient client(String apiKey) {
        return new OkHttpClient.Builder()
                .addInterceptor(apiKeyInterceptor(apiKey))
                .build();
    }

    private Interceptor apiKeyInterceptor(final String apiKey) {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                HttpUrl httpUrl = request.url()
                        .newBuilder()
                        .addQueryParameter("api_key", apiKey)
                        .build();
                request = request.newBuilder()
                        .url(httpUrl)
                        .build();
                return chain.proceed(request);
            }
        };
    }
}
