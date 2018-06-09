package com.andproject.popularmovie1.utilities;

import android.net.Uri;

import com.andproject.popularmovie1.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created on 20180513.
 */

public class uNetwork {
    private final static String stMovieCredential = "YOUR API KEY"; // Insert Your TheMoveCB Api Key Here
    private final static String stMovieUrl = "http://api.themoviedb.org";
    private final static String stMovieParam = "api_key";
    private final static String stMovieVer = "3";
    private final static String stMovieRsc = "movie";

    public static URL buildUrl(String category) {
        Uri builtUri = Uri.parse(stMovieUrl).buildUpon()
                .appendPath(stMovieVer)
                .appendPath(stMovieRsc)
                .appendPath(category)
                .appendQueryParameter(stMovieParam, stMovieCredential)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream input = urlConnection.getInputStream();

            Scanner scanner = new Scanner(input);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
