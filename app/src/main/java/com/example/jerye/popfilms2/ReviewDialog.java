package com.example.jerye.popfilms2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
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

    private Result result;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Intent intent = getActivity().getIntent();
        if(intent!=null){
            Bundle bundle = intent.getExtras();
            result = bundle.getParcelable(ReviewsAdapter.INTENT_FULL_REVIEW);
        }

        TextView author = (TextView) getActivity().findViewById(R.id.full_review_author);
        TextView content = (TextView) getActivity().findViewById(R.id.full_review_content);

        author.setText(result.getAuthor());
        content.setText(result.getContent());

        View dialogView = getActivity().getLayoutInflater().inflate(R.layout.full_review_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogView);
        return builder.create();
    }


}
