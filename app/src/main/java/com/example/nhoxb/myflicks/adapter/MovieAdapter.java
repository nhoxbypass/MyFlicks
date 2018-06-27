package com.example.nhoxb.myflicks.adapter;

import android.content.Context;
import android.content.res.Configuration;
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
import com.example.nhoxb.myflicks.model.Movie;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by nhoxb on 10/12/2016.
 */
public class MovieAdapter extends ArrayAdapter<Movie> {
    public static final int NUMBER_OF_VIEW_TYPE = 2;
    public static final int POPULAR_MOVIE_TYPE = 1;
    public static final int NORMAL_MOVIE_TYPE = 0;
    List<Movie> mMovieList;
    Context mContext;
    public MovieAdapter(Context context, List<Movie> movieList) {
        super(context, -1);
        mContext = context;
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
        if (getItem(position).getAverageVote() > 5.0)
        {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie item = mMovieList.get(position);
        int viewType = getItemViewType(position);
        //Get configuration
        Configuration configuration = mContext.getResources()
                .getConfiguration();

        switch (viewType)
        {
            case NORMAL_MOVIE_TYPE:
                NormalViewHolder normalViewHolder;
                if (convertView ==  null)
                {
                    // Get the data item type for this position

                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_movie,parent,false);

                    normalViewHolder = new NormalViewHolder();
                    normalViewHolder.tvTitle = (TextView) convertView.findViewById(R.id.item_title);
                    normalViewHolder.tvOverview = (TextView) convertView.findViewById(R.id.item_description);
                    normalViewHolder.imageView = (ImageView) convertView.findViewById(R.id.item_image);

                    convertView.setTag(normalViewHolder);
                }
                else
                {
                    normalViewHolder = (NormalViewHolder) convertView.getTag();
                }
                //Fill the data
                normalViewHolder.tvTitle.setText(item.getTitle());
                normalViewHolder.tvOverview.setText(item.getDescription());

                if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
                {
                    Glide.with(mContext)
                            .load(item.getPosterPath())
                            .apply(new RequestOptions()
                                    .placeholder(R.drawable.placeholder_vertical)
                                    .bitmapTransform(new RoundedCornersTransformation(10, 0, RoundedCornersTransformation.CornerType.ALL)))
                            .into(normalViewHolder.imageView);
                }
                else
                {
                    Glide.with(mContext)
                            .load(item.getBackdropPath())
                            .apply(new RequestOptions()
                                    .placeholder(R.drawable.placeholder_horizontal)
                                    .bitmapTransform(new RoundedCornersTransformation(10, 0, RoundedCornersTransformation.CornerType.ALL)))
                            .into(normalViewHolder.imageView);
                }

                break;

            case POPULAR_MOVIE_TYPE:
                PopularViewHolder popularViewHolder;
                if (convertView ==  null)
                {
                    // Get the data item type for this position

                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_popular_movie,parent,false);

                    popularViewHolder = new PopularViewHolder();
                    popularViewHolder.imageView = (ImageView) convertView.findViewById(R.id.popular_item_image);

                    convertView.setTag(popularViewHolder);
                }
                else
                {
                    popularViewHolder = (PopularViewHolder) convertView.getTag();
                }
                //Fill the data
                Glide.with(mContext)
                        .load(item.getBackdropPath())
                        .apply(new RequestOptions()
                                .placeholder(R.drawable.placeholder_horizontal)
                                .bitmapTransform(new RoundedCornersTransformation(10, 0, RoundedCornersTransformation.CornerType.ALL)))
                        .into(popularViewHolder.imageView);
                break;
        }

        return  convertView;
    }

    class NormalViewHolder
    {
        public TextView tvTitle;
        public TextView tvOverview;
        public ImageView imageView;
    }

    class PopularViewHolder
    {
        public ImageView imageView;
    }
}
