package com.example.jerye.popfilms2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jerye.popfilms2.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jerye on 8/13/2017.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {
    private Context mContext;
    private List<String> posterPaths = new ArrayList<>();
    private int count = 0;

    public MoviesAdapter(Context context) {
        mContext = context;
    }

    @Override
    public void onBindViewHolder(MoviesViewHolder holder, int position) {
//        http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg
        Picasso.with(mContext).load("http://image.tmdb.org/t/p/w185/" + posterPaths.get(position)).into(holder.gridPoster);
        Log.d("test", "bind: " + position);

    }

    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.movies_grid_item, parent, false);
        return new MoviesViewHolder(view);

    }

    @Override
    public int getItemCount() {
        Log.d("test", "poster size: " + posterPaths.size());
        return posterPaths.size();
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.grid_poster)
        ImageView gridPoster;
        @BindView(R.id.grid_date)
        TextView gridDate;

        public MoviesViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        public void onClick(View view) {
            getAdapterPosition();
        }


    }

    public void addMovies(String posterPath) {
        posterPaths.add(posterPath);
        Picasso.with(mContext).load("http://image.tmdb.org/t/p/w185/" + posterPath).fetch(new Callback() {
            @Override
            public void onSuccess() {
                Log.d("test", "cached");
                count++;
                if(count >= 20){
                    Log.d("test", "cached 20");
                    notifyDataSetChanged();

                }
            }

            @Override
            public void onError() {
                Log.d("test", "cache error");

            }
        });
    }

    public void finishedAdding(){
        Log.d("test", "finished adding");

    }




}
