package com.urbanutility.jerye.popfilms2.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.urbanutility.jerye.popfilms2.R;
import com.urbanutility.jerye.popfilms2.data.model.credits.Cast;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by jerye on 9/16/2017.
 */

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.CastViewHolder> implements Callback {
    private Context mContext;
    private List<Cast> castList = new ArrayList<>();
    private CastAdapterListener castAdapterListener;
    private int count = 0;
    private Drawable blankProfile;
    private String baseTMDBUrl = "http://image.tmdb.org/t/p/w185/";

    public CastAdapter(Context context, CastAdapterListener castAdapterListener) {
        mContext = context;
        this.castAdapterListener = castAdapterListener;
        Drawable dr = mContext.getDrawable(R.drawable.blank_cast);
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        blankProfile = new BitmapDrawable(mContext.getResources(), Bitmap.createScaledBitmap(bitmap, 185, 278,true));
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
    public void onBindViewHolder(final CastViewHolder holder, int position) {
        String path = castList.get(position).getProfilePath();
        if (path == null) {
            holder.castProfile.setImageDrawable(blankProfile);
        } else {
            Picasso.with(mContext).load(baseTMDBUrl + path).into(holder.castProfile);
        }
        String name = castList.get(position).getName();
        holder.castName.setText(name.replace(' ', '\n'));
        String character = castList.get(position).getCharacter();
        holder.castCharacter.setText(character.replace(' ', '\n'));
    }

    public void addCast(Cast cast) {
        castList.add(cast);
        Picasso.with(mContext).load(baseTMDBUrl + cast.getProfilePath()).fetch(this);

    }

    @Override
    public void onSuccess() {
        count++;

        if (count == 10) {
            notifyDataSetChanged();
            castAdapterListener.onComplete();
        } else if (count > 10) {
            notifyDataSetChanged();
        }
    }

    @Override
    public void onError() {

    }

    public void fetchingComplete() {
        if (count < 10) {
//            notifyDataSetChanged();
//            castAdapterListener.onComplete();
        }
    }

    public class CastViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.cast_name)
        TextView castName;
        @BindView(R.id.cast_profile)
        CircleImageView castProfile;
        @BindView(R.id.cast_character)
        TextView castCharacter;

        CastViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        public void onClick(View view) {
        }
    }

    public interface CastAdapterListener {
        void onComplete();
    }


}
