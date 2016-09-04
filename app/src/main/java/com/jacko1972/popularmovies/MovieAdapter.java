package com.jacko1972.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends ArrayAdapter<MovieInfo> {

    public MovieAdapter(Context context, ArrayList<MovieInfo> movies) {
        super(context, 0, movies);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView poster;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            poster = (ImageView) inflater.inflate(R.layout.movie_image, viewGroup, false);
        } else {
            poster = (ImageView) view;
        }
        MovieInfo movieInfo = getItem(i);
        if (!movieInfo.getBackdrop_path().equals("No Path")) {
            Uri uri = Uri.parse("http://image.tmdb.org/t/p/w185/" + movieInfo.getPoster_path());
            Picasso.with(getContext()).load(uri).into(poster);
        }

        return poster;
    }
}
