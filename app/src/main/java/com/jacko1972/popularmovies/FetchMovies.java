package com.jacko1972.popularmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

class FetchMovies extends AsyncTask<String, Void, ArrayList<MovieInfo>> {

    private static final String TAG = "FetchMovies";
    private boolean failedToConnect = false;
    private final UpdateMovieList updateMovieList;

    public FetchMovies(UpdateMovieList movieList) {
        updateMovieList = movieList;
    }

    private ArrayList<MovieInfo> getMovieDataFromJson(String returnJson) throws JSONException {
        JSONObject data = new JSONObject(returnJson);
        JSONArray results = data.getJSONArray("results");
        ArrayList<MovieInfo> movieArray = new ArrayList<>();

        for (int i = 0; i < results.length(); i++) {
            JSONObject movie = results.getJSONObject(i);
            MovieInfo movieInfo = new MovieInfo(movie.optString("id", "No Id"),
                    movie.optString("backdrop_path", "No Path"),
                    movie.optString("original_title", "No Title"),
                    movie.optString("overview", "No Overview"),
                    movie.optString("poster_path", "No Poster"),
                    movie.optString("release_date", "No Release Date"),
                    movie.optString("vote_average", "No Vote Average"));
            movieArray.add(movieInfo);
        }
        return movieArray;
    }

    @Override
    protected ArrayList<MovieInfo> doInBackground(String... strings) {

        if (strings.length == 0) {
            return null;
        }

        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String moviesJsonResponse = null;

        try {
            final String FORECAST_BASE_URL = "https://api.themoviedb.org/3/movie/";
            final String APP_ID_PARAM = "api_key";

            Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                    .appendPath(strings[0])
                    .appendQueryParameter(APP_ID_PARAM, BuildConfig.MOVIES_API_KEY)
                    .build();

            URL url = new URL(builtUri.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line).append("\n");
            }

            if (buffer.length() == 0) {
                return null;
            }
            moviesJsonResponse = buffer.toString();
        } catch (IOException e) {
            Log.e(TAG, "Error ", e);
            failedToConnect = true;
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(TAG, "Error closing stream", e);
                }
            }
        }

        try {
            return getMovieDataFromJson(moviesJsonResponse);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<MovieInfo> movies) {
        if (failedToConnect) {
            Log.e(TAG, "Failed");
        } else {
            if (updateMovieList != null) {
                updateMovieList.updateMovieList(movies);
            }
        }
    }
}
