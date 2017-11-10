package com.example.jerye.popfilms2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.TextView;

import com.example.jerye.popfilms2.adapter.ReviewsAdapter;
import com.example.jerye.popfilms2.data.model.review.Result;

/**
 * Created by jerye on 10/28/2017.
 */

public class ReviewDialog extends DialogFragment {

    public ReviewDialog() {

    }

    public static ReviewDialog newInstance(Result fullReview) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ReviewsAdapter.INTENT_FULL_REVIEW, fullReview);
        ReviewDialog dialog = new ReviewDialog();
        dialog.setArguments(bundle);
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Result result = this.getArguments().getParcelable(ReviewsAdapter.INTENT_FULL_REVIEW);
        View dialogView = getActivity().getLayoutInflater().inflate(R.layout.full_review_dialog, null);

        TextView author = dialogView.findViewById(R.id.full_review_author);
        TextView content = dialogView.findViewById(R.id.full_review_content);

        StringBuilder sb = new StringBuilder();
        author.setText(sb.append(result.getAuthor()).append("'s Review") );
        content.setText(result.getContent());


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogView);
        return builder.create();
    }


}
