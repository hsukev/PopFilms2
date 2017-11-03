package com.example.jerye.popfilms2.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jerye.popfilms2.R;
import com.example.jerye.popfilms2.data.model.trailer.Result;
import com.example.jerye.popfilms2.util.Utils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jerye on 11/1/2017.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {
    private List<Result> trailerList = new ArrayList<>();
    private Context mContext;

    public TrailerAdapter(Context context) {
        mContext = context;
    }

    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.trailer_item, parent, false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TrailerViewHolder holder, int position) {
        holder.trailer_title.setText(trailerList.get(position).getName());
        String thumbnailUrl = Utils.buildThumbnailUri(trailerList.get(position).getKey());
        Picasso.with(mContext).load(thumbnailUrl).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                holder.trailer_title.setCompoundDrawablesWithIntrinsicBounds(null, new BitmapDrawable(mContext.getResources(), bitmap), null, null);
                Log.d("TrailerAdapter", "success");
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                holder.trailer_title.setCompoundDrawablesWithIntrinsicBounds(null, errorDrawable, null, null);
                Log.d("TrailerAdapter", "failed");

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                holder.trailer_title.setCompoundDrawablesWithIntrinsicBounds(null, placeHolderDrawable, null, null);
                Log.d("TrailerAdapter", "preparing");

            }
        });
    }

    public void addTrailer(Result trailer) {
        trailerList.add(trailer);
        notifyItemInserted(trailerList.size()-1);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return trailerList.size();
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.trailer_title)
        TextView trailer_title;

        TrailerViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        public void onClick(View view) {

        }
    }

}
