package com.andproject.popularmovie1;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.andproject.popularmovie1.model.mdMovie;
import com.squareup.picasso.Picasso;

import static com.andproject.popularmovie1.MainCall.stMainMovie;

public class MovieDetailActivity extends AppCompatActivity {

    private mdMovie movie;

    private ImageView backdropImage;
    private ImageView posterImage;
    private TextView titleText;
    private TextView originalTitleText;
    private TextView releaseText;
    private RatingBar movieRating;
    private TextView informationText;
    private TextView overviewText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);
        if (savedInstanceState == null || !savedInstanceState.containsKey(stMainMovie)) {
            Bundle bundle = getIntent().getExtras();
            movie = bundle.getParcelable(stMainMovie);
        } else {
            movie = savedInstanceState.getParcelable(stMainMovie);
        }

        backdropImage = (ImageView) findViewById(R.id.imgBackdrop);
        titleText = (TextView) findViewById(R.id.txtTitle);
        posterImage = (ImageView)  findViewById(R.id.imgPoster);
        originalTitleText = (TextView) findViewById(R.id.txtOrigTitle);
        releaseText = (TextView) findViewById(R.id.txtRelease);
        movieRating = (RatingBar) findViewById(R.id.movRate);
        informationText = (TextView) findViewById(R.id.txtInfo);
        overviewText = (TextView) findViewById(R.id.txtOverview);

        Button favoriteButton = (Button) findViewById(R.id.btnFavorite);
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MovieDetailActivity.this, "Add as favorite", Toast.LENGTH_LONG)
                        .show();
            }
        });
        displayData();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putParcelable(stMainMovie, movie);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    public void displayData() {
        if (movie != null) {
            Picasso.with(this)
                    .load(movie.getBackdropPath())
                    .into(backdropImage);
            Picasso.with(this)
                    .load(movie.getPosterPath())
                    .into(posterImage);
            titleText.setText(movie.getTitle());
            String originalTitle = String.format(
                    "%s (%s)",
                    movie.getOriginalTitle(),
                    movie.getOriginalLanguage()
            );
            originalTitleText.setText(originalTitle);
            releaseText.setText(movie.getReleaseDate());
            movieRating.setRating(movie.getVoteAverage() / 2);
            String information = String.format(
                    "%.2f/10 of %d",
                    movie.getVoteAverage(),
                    movie.getVoteCount()
            );
            informationText.setText(information);
            overviewText.setText(movie.getOverview());
        }
    }
}
