package com.example.jerye.popfilms2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jerye.popfilms2.adapter.MoviesAdapter;
import com.example.jerye.popfilms2.data.model.GenreScheme;
import com.example.jerye.popfilms2.data.model.Result;
import com.example.jerye.popfilms2.util.Circle;
import com.example.jerye.popfilms2.util.CircleAngleAnimation;
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
    @BindView(R.id.detailed_summary)
    TextView summary;
    @BindView(R.id.detailed_genre)
    TextView genreList;

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
        Picasso.with(this).load("http://image.tmdb.org/t/p/w500/" + movie.getBackdropPath()).into(background);
        title.setText(movie.getOriginalTitle());
        releaseDate.setText(movie.getReleaseDate());
        summary.setText(movie.getOverview());
        genreList.setText(GenreScheme.getGenre(movie.getGenreIds()));

        float ratingAngle = movie.getVoteAverage()*360/10;
        CircleAngleAnimation circleAngleAnimation = new CircleAngleAnimation(rating,ratingAngle);
        circleAngleAnimation.setDuration(3000);
        circleAngleAnimation.setInterpolator(new FastOutSlowInInterpolator());
        rating.startAnimation(circleAngleAnimation);
    }

}
