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
import com.example.nhoxb.myflicks.R;
import com.example.nhoxb.myflicks.model.Movie;

import java.util.List;

/**
 * Created by nhoxb on 10/12/2016.
 */
public class MovieAdapter extends ArrayAdapter<Movie> {
    List<Movie> mMovieList;
    Context mContext;
    public MovieAdapter(Context context, List<Movie> movieList) {
        super(context, -1);
        mContext = context;
        mMovieList = movieList;
    }

    @Override
    public Movie getItem(int position) {
        return mMovieList.get(position);
    }

    @Override
    public int getCount() {
        return mMovieList.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView ==  null)
        {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_movie,parent,false);

            viewHolder = new ViewHolder();
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.item_title);
            viewHolder.tvOverview = (TextView) convertView.findViewById(R.id.item_description);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //Fill the data
        Movie item = mMovieList.get(position);
        viewHolder.tvTitle.setText(item.getTitle());
        viewHolder.tvOverview.setText(item.getDescription());


        //Get configuration

        Configuration configuration = mContext.getResources()
                .getConfiguration();

        if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            Glide.with(mContext)
                    .load(item.getPosterPath())
                    .into(viewHolder.imageView);
        }
        else
        {
            Glide.with(mContext)
                    .load(item.getBackdropPath())
                    .into(viewHolder.imageView);
        }

        return  convertView;
    }

    class ViewHolder
    {
        public TextView tvTitle;
        public TextView tvOverview;
        public ImageView imageView;
    }
}
