package com.example.jerye.popfilms2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jerye.popfilms2.R;
import com.example.jerye.popfilms2.data.model.Cast;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jerye on 9/16/2017.
 */

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.CastViewHolder> implements Callback{
    private Context mContext;
    private List<Cast> castList = new ArrayList<>();
    private CastAdapterListener castAdapterListener;
    private int count = 0;

    public CastAdapter(Context context, CastAdapterListener castAdapterListener) {
        mContext = context;
        this.castAdapterListener = castAdapterListener;

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
        Picasso.with(mContext).load("http://image.tmdb.org/t/p/w185/" + castList.get(position).getProfilePath()).into(holder.castProfile);
        holder.castName.setText(castList.get(position).getName());
    }

    public void addCast(Cast cast) {
        castList.add(cast);
        Picasso.with(mContext).load("http://image.tmdb.org/t/p/w185/" + cast.getProfilePath()).fetch(this);

    }

    @Override
    public void onSuccess() {
        count++;
        if(count == 10){
            notifyDataSetChanged();
            castAdapterListener.onComplete();
        }else if(count > 10){
            notifyDataSetChanged();
        }
    }

    @Override
    public void onError() {

    }

    public class CastViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.cast_name)
        TextView castName;
        @BindView(R.id.cast_profile)
        ImageView castProfile;

        CastViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }

        @Override
        public void onClick(View view) {

        }
    }

    public interface CastAdapterListener{
        void onComplete();
    }

}
