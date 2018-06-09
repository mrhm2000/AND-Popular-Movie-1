package com.andproject.popularmovie1.utilities;

import com.andproject.popularmovie1.model.mdMovie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created on 20180513.
 */

public class uJson {


    static final String stMvId = "id";
    static final String stMvResult = "results";
    static final String stMvVoteCount = "vote_count";
    static final String stMvVoteAvg = "vote_average";
    static final String stMvVid = "video";
    static final String stMvTitle = "title";
    static final String stMvPop = "popularity";
    static final String stMvPosterPath = "poster_path";
    static final String stMvOrgTitle = "original_title";
    static final String stMvOrgLan = "original_language";
    static final String stMvBackPath = "backdrop_path";
    static final String stMvAdult = "adult";
    static final String stMvRelDate = "release_date";
    static final String stMvOverview = "overview";

    public static mdMovie[] getMovies(String json) throws JSONException {
        mdMovie[] movies = null;
        JSONObject moviesJson = new JSONObject(json);
        if (moviesJson.has(stMvResult)) {
            JSONArray moviesArray = moviesJson.getJSONArray(stMvResult);
            movies = new mdMovie[moviesArray.length()];

            for (int i = 0; i < moviesArray.length(); i++) {
                JSONObject movieJson = moviesArray.getJSONObject(i);
                mdMovie movie = new mdMovie();
                int id = movieJson.getInt(stMvId);
                movie.setId(id);

                String title = movieJson.getString(stMvTitle);
                movie.setTitle(title);

                String originalTitle = movieJson.getString(stMvOrgTitle);
                movie.setOriginalTitle(originalTitle);

                String overview = movieJson.getString(stMvOverview);
                movie.setOverview(overview);

                String originalLanguage = movieJson.getString(stMvOrgLan);
                movie.setOriginalLanguage(originalLanguage);

                String releaseDate = movieJson.getString(stMvRelDate);
                movie.setReleaseDate(releaseDate);

                int voteCount = movieJson.getInt(stMvVoteCount);
                movie.setVoteCount(voteCount);

                double voteAverage = movieJson.getDouble(stMvVoteAvg);
                movie.setVoteAverage((float) voteAverage);

                double popularity = movieJson.getDouble(stMvPop);
                movie.setPopularity((float) popularity);

                String posterPath = movieJson.getString(stMvPosterPath);
                movie.setPosterPath(posterPath);

                String backdropPath = movieJson.getString(stMvBackPath);
                movie.setBackdropPath(backdropPath);

                boolean video = movieJson.getBoolean(stMvVid);
                movie.setVideo(video);

                boolean adult = movieJson.getBoolean(stMvAdult);
                movie.setAdult(adult);

                movies[i] = movie;
            }
        }
        return movies;
    }
}
