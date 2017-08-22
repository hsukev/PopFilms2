package com.example.jerye.popfilms2.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by jerye on 8/21/2017.
 */

public class MovieScrollListener extends RecyclerView.OnScrollListener {
    private int RELOAD_BUFFER = 2;
    private GridLayoutManager mLayoutManager;
    private ReloadListener mReloadListener;

    public MovieScrollListener(GridLayoutManager gridLayoutManager, ReloadListener reloadListener) {
        super();
        mLayoutManager = gridLayoutManager;
        mReloadListener = reloadListener;

    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int count = mLayoutManager.getItemCount();
        int position = mLayoutManager.findLastVisibleItemPosition();

        Log.d("test", "last visible view: " + position);

        if(position > count - RELOAD_BUFFER){
            mReloadListener.loadMore();
        }
    }

    public interface ReloadListener{
        void loadMore();
    }
}
