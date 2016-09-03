package com.jacko1972.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        Intent intent = getActivity().getIntent();

        //int position = intent.getIntExtra("movieInfoPosition", -1);
        MovieInfo movieInfo = intent.getParcelableExtra("parcelMovie");

        if (movieInfo != null) {
            // Set title
            getActivity().setTitle(movieInfo.getOriginal_title());

            //Set Image
            ImageView movieImageView = (ImageView) rootView.findViewById(R.id.movie_detail_image_view);
            if (!movieInfo.getBackdrop_path().equals("No Path")) {
                Uri uri = Uri.parse("http://image.tmdb.org/t/p/w500/" + movieInfo.getPoster_path());
                Picasso.with(getActivity()).load(uri).into(movieImageView);
            }

            //Set Synopsis
            TextView synopsis = (TextView) rootView.findViewById(R.id.synopsis);
            synopsis.setText(movieInfo.getOverview());

            //Set Release Date
            TextView releaseDate = (TextView) rootView.findViewById(R.id.release_date);
            releaseDate.setText(movieInfo.getRelease_date());

            //Set User Rating
            TextView userRating = (TextView) rootView.findViewById(R.id.user_rating);
            userRating.setText(movieInfo.getVote_average());
        }
        return rootView;
    }
}
