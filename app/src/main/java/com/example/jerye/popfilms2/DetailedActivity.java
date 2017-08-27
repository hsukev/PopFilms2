package com.example.jerye.popfilms2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jerye.popfilms2.adapter.MoviesAdapter;
import com.example.jerye.popfilms2.data.model.Result;
import com.example.jerye.popfilms2.util.Circle;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jerye on 8/22/2017.
 */

public class DetailedActivity extends AppCompatActivity {
    @BindView(R.id.detailed_background)
    ImageView background;
    @BindView(R.id.detailed_title)
    TextView title;
    @BindView(R.id.detailed_release_date)
    TextView releaseDate;
    @BindView(R.id.detailed_rating)
    Circle rating;

    Result movie;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        movie = retrieveMovie();
        populateViews();
    }

    public Result retrieveMovie() {
        Bundle bundle = getIntent().getBundleExtra(MoviesAdapter.INTENT_KEY);
        return (Result) bundle.getSerializable(MoviesAdapter.BUNDLE_KEY);
    }

    public void populateViews(){
        Picasso.with(this).load(movie.getBackdropPath()).into(background);
        title.setText(movie.getOriginalTitle());
        releaseDate.setText(movie.getReleaseDate());
    }

}
