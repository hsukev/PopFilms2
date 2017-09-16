package com.example.jerye.popfilms2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jerye.popfilms2.R;
import com.example.jerye.popfilms2.data.model.Cast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jerye on 9/16/2017.
 */

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.CastViewHolder> {
    private Context mContext;
    private List<Cast> castList = new ArrayList<>();

    public CastAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getItemCount() {
        return castList.size();
    }

    @Override
    public CastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cast_item, parent, false);
        return new CastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CastViewHolder holder, int position) {

    }

    public void addCast(Cast cast) {
        castList.add(cast);
        notifyDataSetChanged();
    }

    public class CastViewHolder extends RecyclerView.ViewHolder {
        CastViewHolder(View view) {
            super(view);
        }

    }

}
