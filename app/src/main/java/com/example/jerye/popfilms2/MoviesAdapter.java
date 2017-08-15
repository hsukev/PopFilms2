package com.example.jerye.popfilms2;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jerye on 8/13/2017.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {
    private Cursor mCursor;
    private Context mContext;

    public MoviesAdapter(Context context){
        mContext = context;
    }

    @Override
    public void onBindViewHolder(MoviesViewHolder holder, int position) {

    }

    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.movies_grid_item,parent, false);
        return new MoviesViewHolder(view);

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public MoviesViewHolder(View view){
            super(view);
        }

        @Override
        public void onClick(View view) {
            mCursor.getPosition();
        }
    }

    public void refreshCursor(Cursor cursor){
        mCursor = cursor;
        notifyDataSetChanged();
    }

    public void setUpNetwork(){
        Retrofit retrofitCclient = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/movie/popular?api_key=<<api_key>>&language=en-US&page=1")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
