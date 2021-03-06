package com.jacko1972.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private GridView gridView;
    public static MovieAdapter movieAdapter;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        gridView = (GridView) rootView.findViewById(R.id.movie_grid_view);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovieInfo();
    }

    private void updateMovieInfo() {
        boolean internet = internetAvailable();
        if (internet) {

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            final String movie_list = sharedPreferences.getString(getString(R.string.prefs_movie_list), getString(R.string.prefs_movie_list_default_value));
            if (movie_list.equals("top_rated")) {
                getActivity().setTitle("Top Rated Movies");
            } else {
                getActivity().setTitle("Popular Movies");
            }
            FetchMovies fetchMovies = new FetchMovies();
            fetchMovies.execute(movie_list);
            movieAdapter = new MovieAdapter(getActivity());
            gridView.setAdapter(movieAdapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    intent.putExtra("movieInfoPosition", i);
                    startActivity(intent);
                }
            });
        } else {
            Toast.makeText(getActivity(), "No Internet Connection!! Please connect to load the Movie Information.", Toast.LENGTH_LONG).show();
        }
    }

    private boolean internetAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
