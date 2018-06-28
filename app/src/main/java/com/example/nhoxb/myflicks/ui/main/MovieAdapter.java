package com.example.nhoxb.myflicks.ui.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.nhoxb.myflicks.R;
import com.example.nhoxb.myflicks.data.remote.model.Movie;
import com.example.nhoxb.myflicks.utils.DeviceUtils;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by nhoxb on 10/12/2016.
 */
public class MovieAdapter extends ArrayAdapter<Movie> {
    private static final int NUMBER_OF_VIEW_TYPE = 2;
    private static final int POPULAR_MOVIE_TYPE = 1;
    private static final int NORMAL_MOVIE_TYPE = 0;
    private List<Movie> mMovieList;

    public MovieAdapter(Context context, List<Movie> movieList) {
        super(context, -1);
        mMovieList = movieList;
    }

    // Returns the number of types of Views that will be created by getView(int, View, ViewGroup)
    @Override
    public int getViewTypeCount() {
        // Returns the number of types of Views that will be created by this adapter
        // Each type represents a set of views that can be converted
        return NUMBER_OF_VIEW_TYPE;
    }

    // Get the type of View that will be created by getView(int, View, ViewGroup)
    // for the specified item.
    @Override
    public int getItemViewType(int position) {
        // Return an integer here representing the type of View.
        // Note: Integers must be in the range 0 to getViewTypeCount() - 1
        if (getItem(position).getAverageVote() > 5.0) {
            //Type 2 is popular movies
            return POPULAR_MOVIE_TYPE;
        }
        return NORMAL_MOVIE_TYPE;
    }

    @Override
    public Movie getItem(int position) {
        return mMovieList.get(position);
    }

    @Override
    public int getCount() {
        return mMovieList.size();
    }


    // Get a View that displays the data at the specified position in the data set.
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Movie item = mMovieList.get(position);

        switch (getItemViewType(position)) {
            case NORMAL_MOVIE_TYPE:
                NormalViewHolder normalViewHolder;
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_movie, parent, false);
                    normalViewHolder = new NormalViewHolder(convertView);
                    convertView.setTag(normalViewHolder);
                } else {
                    normalViewHolder = (NormalViewHolder) convertView.getTag();
                }

                // Fill the data
                bindNormalMovieData(normalViewHolder, item);

                break;

            case POPULAR_MOVIE_TYPE:
                PopularViewHolder popularViewHolder;
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_popular_movie, parent, false);
                    popularViewHolder = new PopularViewHolder(convertView);
                    convertView.setTag(popularViewHolder);
                } else {
                    popularViewHolder = (PopularViewHolder) convertView.getTag();
                }

                // Fill the data
                bindPopularMovieData(popularViewHolder, item);
                break;
        }

        return convertView;
    }

    private void bindNormalMovieData(NormalViewHolder normalViewHolder, Movie item) {
        normalViewHolder.tvTitle.setText(item.getTitle());
        normalViewHolder.tvOverview.setText(item.getDescription());

        if (DeviceUtils.isPortraitOrientation(getContext())) {
            Glide.with(getContext())
                    .load(item.getPosterPath())
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.placeholder_vertical)
                            .bitmapTransform(new RoundedCornersTransformation(10, 0, RoundedCornersTransformation.CornerType.ALL)))
                    .into(normalViewHolder.imageView);
        } else {
            Glide.with(getContext())
                    .load(item.getBackdropPath())
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.placeholder_horizontal)
                            .bitmapTransform(new RoundedCornersTransformation(10, 0, RoundedCornersTransformation.CornerType.ALL)))
                    .into(normalViewHolder.imageView);
        }
    }

    private void bindPopularMovieData(PopularViewHolder popularViewHolder, Movie item) {
        Glide.with(getContext())
                .load(item.getBackdropPath())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.placeholder_horizontal)
                        .bitmapTransform(new RoundedCornersTransformation(10, 0, RoundedCornersTransformation.CornerType.ALL)))
                .into(popularViewHolder.imageView);
    }

    class NormalViewHolder {
        TextView tvTitle;
        TextView tvOverview;
        ImageView imageView;

        NormalViewHolder(View v) {
            tvTitle = v.findViewById(R.id.item_title);
            tvOverview = v.findViewById(R.id.item_description);
            imageView = v.findViewById(R.id.item_image);
        }
    }

    class PopularViewHolder {
        ImageView imageView;

        PopularViewHolder(View v) {
            imageView = v.findViewById(R.id.popular_item_image);
        }
    }
}
