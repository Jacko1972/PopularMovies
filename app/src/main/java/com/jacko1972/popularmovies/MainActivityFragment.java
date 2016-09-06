package com.jacko1972.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivityFragment extends Fragment implements UpdateMovieList {

    private MovieAdapter movieAdapter;
    private final ArrayList<MovieInfo> movieInfo = new ArrayList<>();
    private static final String TAG = "MainActivityFragment";

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        GridView gridView = (GridView) rootView.findViewById(R.id.movie_grid_view);
        movieAdapter = new MovieAdapter(getActivity(), movieInfo);
        gridView.setAdapter(movieAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                MovieInfo selectedMovie = movieAdapter.getItem(i);
                intent.putExtra("parcelMovie", selectedMovie);
                startActivity(intent);
            }
        });
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

            MovieDbInterface dbInterface = MovieDbService.getClient().create(MovieDbInterface.class);
            Call<FullMovieJsonResponse> makeCall = dbInterface.getMovieByListType(movie_list, BuildConfig.MOVIES_API_KEY);
            makeCall.enqueue(new Callback<FullMovieJsonResponse>() {
                @Override
                public void onResponse(Call<FullMovieJsonResponse> call, Response<FullMovieJsonResponse> response) {
                    List<MovieInfo> movieInfoList = response.body().getResults();
                    ArrayList<MovieInfo> movieInfoArrayList = new ArrayList<MovieInfo>();
                    movieInfoArrayList.addAll(movieInfoList);
                    updateMovieList(movieInfoArrayList);
                }

                @Override
                public void onFailure(Call<FullMovieJsonResponse> call, Throwable t) {
                    Log.d(TAG, "call: " + call);
                    Log.d(TAG, "throws: " + t.toString());
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

    @Override
    public void updateMovieList(ArrayList<MovieInfo> movies) {
        movieAdapter.clear();
        movieInfo.clear();
        movieInfo.addAll(movies);
        movieAdapter.addAll(movieInfo);
        movieAdapter.notifyDataSetChanged();
    }
}
