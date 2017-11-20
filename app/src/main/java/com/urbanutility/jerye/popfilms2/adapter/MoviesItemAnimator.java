package com.urbanutility.jerye.popfilms2.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.animation.DecelerateInterpolator;

import com.urbanutility.jerye.popfilms2.util.Utils;

/**
 * Created by jerye on 8/19/2017.
 */

public class MoviesItemAnimator extends DefaultItemAnimator {
    private int lastAddedItemAnimation = -6;

    @Override
    public boolean canReuseUpdatedViewHolder(@NonNull RecyclerView.ViewHolder viewHolder) {
        return true;
    }

    @Override
    public boolean animateAdd(RecyclerView.ViewHolder holder) {
        if (holder.getLayoutPosition() > lastAddedItemAnimation) {
            lastAddedItemAnimation++;
            runEnterAnimatmion((MoviesAdapter.MoviesViewHolder) holder);
            return false;
        }
        return false;
    }

    public void runEnterAnimatmion(final MoviesAdapter.MoviesViewHolder moviesVH) {
        int screenHeight = Utils.getScreenHeight(moviesVH.itemView.getContext());
        moviesVH.itemView.setTranslationY(screenHeight);
        moviesVH.itemView.animate()
                .setStartDelay(moviesVH.getAdapterPosition() * 100)
                .setDuration(700)
                .setInterpolator(new DecelerateInterpolator(3.f))
                .translationY(0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        dispatchAddFinished(moviesVH);
                    }
                })
                .start();
    }

    @Override
    public boolean isRunning() {
        return super.isRunning();
    }
}
