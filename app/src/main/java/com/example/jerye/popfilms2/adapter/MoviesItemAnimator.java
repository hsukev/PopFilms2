package com.example.jerye.popfilms2.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;

/**
 * Created by jerye on 8/19/2017.
 */

public class MoviesItemAnimator extends DefaultItemAnimator{

    public MoviesItemAnimator() {
        super();
    }

    @Override
    public boolean canReuseUpdatedViewHolder(@NonNull RecyclerView.ViewHolder viewHolder) {
        return true;
    }

    @Override
    public boolean animateAdd(RecyclerView.ViewHolder holder) {
        return super.animateAdd(holder);
    }
}
