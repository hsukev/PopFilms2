package com.example.jerye.popfilms2.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jerye.popfilms2.DetailedActivity;
import com.example.jerye.popfilms2.R;
import com.example.jerye.popfilms2.data.model.movies.Result;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jerye on 8/13/2017.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> implements Callback{
    private Context mContext;
    private MovieAdapterListener movieAdapterListener;
    private List<Result> moviesList = new ArrayList<>();
    private int count = 0;
    private String queryType, toggleType;
    public static final String BUNDLE_KEY = "bundle key";
    public static final String INTENT_KEY = "intent key";


    public MoviesAdapter(Context context, String queryType,String toggleType, MovieAdapterListener movieAdapterListener) {
        mContext = context;
        this.queryType = queryType;
        this.toggleType = toggleType;
        this.movieAdapterListener = movieAdapterListener;
    }

    @Override
    public void onBindViewHolder(MoviesViewHolder holder, int position) {
//        http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg
        Log.d("test", "bind view");
        holder.gridTitle.setText(moviesList.get(position).getTitle());
        Picasso.with(mContext).load("http://image.tmdb.org/t/p/w185/" + moviesList.get(position).getPosterPath()).into(holder.gridPoster);

        switch(queryType){
            case "popular":
                holder.gridAttribute.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_favorite_black_24dp,0,0,0);
                holder.gridAttribute.setText(String.valueOf(Math.round(moviesList.get(position).getPopularity())));
                break;
            case "top_rated":
                holder.gridAttribute.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_star_black_24dp,0,0,0);
                holder.gridAttribute.setText(String.valueOf(moviesList.get(position).getVoteAverage()));
                break;
            case "upcoming":
                SimpleDateFormat readFormat = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat writeFormat = new SimpleDateFormat("MMMM dd yyyy");
                Date date;
                StringBuilder sb = new StringBuilder();
                try {
                    date = readFormat.parse(moviesList.get(position).getReleaseDate());
                    sb.append(writeFormat.format(date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                holder.gridAttribute.setText(sb.toString());

                break;
        }
    }

    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.movies_grid_item, parent, false);
        return new MoviesViewHolder(view);

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.grid_poster)
        ImageView gridPoster;
        @BindView(R.id.grid_title)
        TextView gridTitle;
        @BindView(R.id.grid_attribute)
        TextView gridAttribute;

        public MoviesViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d("test", "position: " + getAdapterPosition());
            Bundle bundle = new Bundle();
            bundle.putSerializable(BUNDLE_KEY, moviesList.get(getAdapterPosition()));
            Intent intent = new Intent(mContext, DetailedActivity.class).putExtra(INTENT_KEY, bundle);
            bundle.putString("movieTvToggle",toggleType);
            mContext.startActivity(intent);
        }


    }

    public void addMovies(Result result) {
        moviesList.add(result);
        Picasso.with(mContext).load("http://image.tmdb.org/t/p/w185/" + result.getPosterPath()).fetch(this);
    }

    public void clearData(){
        moviesList.clear();
        notifyDataSetChanged();
    }

    @Override
    public void onSuccess() {
        count++;
        if(count == 20){
            notifyDataSetChanged();
            movieAdapterListener.onComplete();
        }else if(count > 20){
            notifyDataSetChanged();
        }
    }

    @Override
    public void onError() {
        Log.d("test", "cache error");
    }

    public void finishedAdding(){
        Log.d("test", "finished adding");

    }

    public interface MovieAdapterListener{
        void onComplete();

    }






}
