package com.andproject.popularmovie1;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.andproject.popularmovie1.model.mdMovie;
import com.andproject.popularmovie1.utilities.uJson;
import com.andproject.popularmovie1.utilities.uNetwork;
import org.json.JSONException;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    private RecyclerView rvMovieView;
    private MovieAdapter maMovieAdapter;
    private TextView tvErrMsg;
    private ProgressBar pbLoadBar;
    private mdMovie[] mvMovies;
    private String stCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_main);
        tvErrMsg = (TextView) findViewById(R.id.text_error);
        pbLoadBar = (ProgressBar) findViewById(R.id.loading_progress);
        rvMovieView = (RecyclerView) findViewById(R.id.movies_view);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
        rvMovieView.setLayoutManager(layoutManager);
        rvMovieView.setHasFixedSize(true);
        maMovieAdapter = new MovieAdapter(this);
        maMovieAdapter.setMovies(mvMovies);
        rvMovieView.setAdapter(maMovieAdapter);

        if (savedInstanceState == null) {
            stCategory = MainCall.stCatPop;
            requestMovies();
        } else {
            stCategory = savedInstanceState.getString(MainCall.stMainCat);
            Parcelable[] parcelableMovies = savedInstanceState.getParcelableArray(MainCall.stMainMovies);
            if (parcelableMovies != null) {
                mvMovies = Arrays.copyOf(parcelableMovies, parcelableMovies.length, mdMovie[].class);
                maMovieAdapter.setMovies(mvMovies);
            } else {
                requestMovies();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movie, menu);
        MenuItem menuItem = menu.findItem(R.id.action_category);
        updateMenuTitle(menuItem);
        return true;
    }

    public void updateMenuTitle(MenuItem menuItem) {
        if (stCategory.equals(MainCall.stCatPop))
            menuItem.setTitle(getString(R.string.action_top_rated));
        else
            menuItem.setTitle(getString(R.string.action_popular));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArray(MainCall.stMainMovies, mvMovies);
        outState.putString(MainCall.stMainCat, stCategory);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_category:
                stCategory = (stCategory.equals(MainCall.stCatPop))
                        ? MainCall.stCatRated
                        : MainCall.stCatPop;
                updateMenuTitle(item);
            case R.id.action_refresh:
                requestMovies();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isConnectedOrConnecting();
    }

    private void requestMovies() {
        if (isOnline()) {
            Toast.makeText(this, "Updating " + stCategory + " Movies.", Toast.LENGTH_LONG)
                    .show();
            new TheMovieTask().execute(stCategory);
        } else {
            Toast.makeText(this, getString(R.string.connection_error), Toast.LENGTH_LONG)
                    .show();
        }
    }

    private void showMovieDataView() {
        tvErrMsg.setVisibility(View.INVISIBLE);
        rvMovieView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        rvMovieView.setVisibility(View.INVISIBLE);
        tvErrMsg.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(mdMovie movie) {
        Intent detailIntent = new Intent(this, MovieDetailActivity.class);
        detailIntent.putExtra(MainCall.stMainMovie, movie);
        startActivity(detailIntent);
    }

    class TheMovieTask extends AsyncTask<String, Void, mdMovie[]> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pbLoadBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected mdMovie[] doInBackground(String... params) {
            String stCategory = params[0];
            mdMovie[] mvMovies = null;
            try {
                URL requestUrl = uNetwork.buildUrl(stCategory);
                String response = uNetwork.getResponseFromHttpUrl(requestUrl);
                mvMovies = uJson.getMovies(response);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return mvMovies;
        }

        @Override
        protected void onPostExecute(mdMovie[] moviesData) {
            pbLoadBar.setVisibility(View.INVISIBLE);
            if (moviesData != null) {
                mvMovies = moviesData;
                showMovieDataView();
                maMovieAdapter.setMovies(mvMovies);
            } else {
                showErrorMessage();
            }
        }

    }
}