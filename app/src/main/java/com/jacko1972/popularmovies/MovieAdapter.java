package com.jacko1972.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MovieAdapter extends BaseAdapter {

    private final Context context;

    public MovieAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return MainActivity.movieInfo.size();
    }

    @Override
    public Object getItem(int i) {
        return MainActivity.movieInfo.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
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
        MovieInfo movieInfo = MainActivity.movieInfo.get(i);
        if (!movieInfo.getBackdrop_path().equals("No Path")) {
            Uri uri = Uri.parse("http://image.tmdb.org/t/p/w185/" + movieInfo.getPoster_path());
            Picasso.with(context).load(uri).into(poster);
        }

        return poster;
    }
}
