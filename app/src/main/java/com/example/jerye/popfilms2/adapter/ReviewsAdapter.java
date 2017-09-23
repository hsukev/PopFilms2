package com.example.jerye.popfilms2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jerye.popfilms2.R;
import com.example.jerye.popfilms2.data.model.review.Result;
import com.squareup.picasso.Callback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jerye on 9/16/2017.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder> implements Callback{
    private Context mContext;
    private List<Result> reviewsList = new ArrayList<>();
    private ReviewsAdapterListener reviewsAdapterListener;
    private int count = 0;

    public ReviewsAdapter(Context context, ReviewsAdapterListener reviewsAdapterListener) {
        mContext = context;
        this.reviewsAdapterListener = reviewsAdapterListener;

    }

    @Override
    public int getItemCount() {
        return reviewsList.size();
    }

    @Override
    public ReviewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.review_item, parent, false);
        return new ReviewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewsViewHolder holder, int position) {
        holder.reviewsAuthor.setText(reviewsList.get(position).getAuthor());
        holder.reviewsContent.setText(reviewsList.get(position).getContent());
    }

    public void addReviews(Result result) {
        reviewsList.add(result);


    }

    @Override
    public void onSuccess() {
        count++;
        if(count == 10){
            notifyDataSetChanged();
            reviewsAdapterListener.onComplete();
        }else if(count > 10){
            notifyDataSetChanged();
        }
    }

    @Override
    public void onError() {

    }

    public class ReviewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.reviews_author)
        TextView reviewsAuthor;
        @BindView(R.id.reviews_content)
        TextView reviewsContent;

        ReviewsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }

        @Override
        public void onClick(View view) {

        }
    }

    public interface ReviewsAdapterListener{
        void onComplete();
    }

}
