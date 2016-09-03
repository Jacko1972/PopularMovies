package com.jacko1972.popularmovies;


import android.os.Parcel;
import android.os.Parcelable;

class MovieInfo implements Parcelable {
    //String adult;
    private String backdrop_path;
    //Array genre_ids;
    private String id;
    //String original_language;
    private String original_title;
    private String overview;
    private String release_date;
    private String poster_path;
    //String popularity;
    //String title;
    //String video;
    private String vote_average;
    //String vote_count;


//    public MovieInfo() {
//    }

    public MovieInfo(String id, String backdrop_path, String original_title, String overview, String poster_path, String release_date, String vote_average) {
        this.id = id;
        this.backdrop_path = backdrop_path;
        this.original_title = original_title;
        this.overview = overview;
        this.poster_path = poster_path;
        this.release_date = release_date;
        this.vote_average = vote_average;
    }

    protected MovieInfo(Parcel in) {
        backdrop_path = in.readString();
        id = in.readString();
        original_title = in.readString();
        overview = in.readString();
        release_date = in.readString();
        poster_path = in.readString();
        vote_average = in.readString();
    }

    public static final Creator<MovieInfo> CREATOR = new Creator<MovieInfo>() {
        @Override
        public MovieInfo createFromParcel(Parcel in) {
            return new MovieInfo(in);
        }

        @Override
        public MovieInfo[] newArray(int size) {
            return new MovieInfo[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    //public void setBackdrop_path(String backdrop_path) {
    //    this.backdrop_path = backdrop_path;
    //}

    public String getOriginal_title() {
        return original_title;
    }

    //public void setOriginal_title(String original_title) {
    //    this.original_title = original_title;
    //}

    public String getOverview() {
        return overview;
    }

    //public void setOverview(String overview) {
    //    this.overview = overview;
    //}

    public String getPoster_path() {
        return poster_path;
    }

    //public void setPoster_path(String poster_path) {
    //    this.poster_path = poster_path;
    //}

    public String getRelease_date() {
        return release_date;
    }

    //public void setRelease_date(String release_date) {
    //    this.release_date = release_date;
    //}

    public String getVote_average() {
        return vote_average;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(backdrop_path);
        parcel.writeString(id);
        parcel.writeString(original_title);
        parcel.writeString(overview);
        parcel.writeString(release_date);
        parcel.writeString(poster_path);
        parcel.writeString(vote_average);
    }

    //public void setVote_average(String vote_average) {
    //    this.vote_average = vote_average;
    //}
}
