package com.urbanutility.jerye.popfilms2.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.urbanutility.jerye.popfilms2.R;
import com.urbanutility.jerye.popfilms2.data.model.trailer.Result;
import com.urbanutility.jerye.popfilms2.util.Utils;

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
    private TrailerAdapterListener trailerAdapterListener;

    public TrailerAdapter(Context context, TrailerAdapterListener trailerAdapterListener) {
        mContext = context;
        this.trailerAdapterListener = trailerAdapterListener;
    }

    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.trailer_item, parent, false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TrailerViewHolder holder, final int position) {
        final String thumbnailUrl = Utils.buildThumbnailUri(trailerList.get(position).getKey());
        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                holder.trailer_title.setText(trailerList.get(position).getName());
                ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(bitmap.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
                holder.trailer_thumbnail.setImageDrawable(new BitmapDrawable(mContext.getResources(),bitmap));
//                Log.d("bitmap","width:" + bitmap.getWidth() + "dps:" + dps);
                holder.trailer_title.setLayoutParams(params);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        holder.trailer_thumbnail.setTag(target);
        Picasso.with(mContext).load(thumbnailUrl).into(target);
    }

    public void addTrailer(Result trailer) {
        trailerList.add(trailer);
        notifyItemInserted(trailerList.size() - 1);
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
        @BindView(R.id.trailer_thumbnail)
        ImageView trailer_thumbnail;

        TrailerViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            trailerAdapterListener.onTrailerClick(trailerList.get(getAdapterPosition()).getKey());

        }
    }

    public interface TrailerAdapterListener{
        void onTrailerClick(String youtubeId);
    }


}
