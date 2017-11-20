package com.urbanutility.jerye.popfilms2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.urbanutility.jerye.popfilms2.R;
import com.urbanutility.jerye.popfilms2.data.model.review.Result;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jerye on 9/16/2017.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder> {
    private Context mContext;
    private List<Result> reviewsList = new ArrayList<>();
    private ReviewsAdapterListener reviewsAdapterListener;
    public static final String INTENT_FULL_REVIEW = "full_review";

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
        StringBuilder sb = new StringBuilder();
        sb.append('"').append(reviewsList.get(position).getContent()).append('"');
        holder.reviewsContent.setText(sb.toString());
    }

    public void addReviews(Result result) {
        reviewsList.add(result);
        notifyDataSetChanged();
    }



    public class ReviewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.reviews_author)
        TextView reviewsAuthor;
        @BindView(R.id.reviews_content)
        TextView reviewsContent;

        ReviewsViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            ButterKnife.bind(this, view);
        }

        @Override
        public void onClick(View view) {
            reviewsAdapterListener.onClick(reviewsList.get(getAdapterPosition()));

        }
    }

    public interface ReviewsAdapterListener {
        void onClick(Result fullReview);

    }


}
