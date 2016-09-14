package com.sparks.techie.moviestation.Util;

import android.net.Uri;

/**
 * Created by Sailesh on 8/1/16.
 */

public class UriBuilderUtil {
    public static String getURLForNowPlaying(){
        Uri.Builder builder= new Uri.Builder();

        builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath("now_playing")
                .appendQueryParameter("api_key",Constants.API_KEY);

        return builder.build().toString();
    }

    public static String getURLForNowPlayingWithPageNumber(int pageNumber){
        Uri.Builder builder= new Uri.Builder();

        builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath("now_playing")
                .appendQueryParameter("api_key",Constants.API_KEY)
                .appendQueryParameter("page",pageNumber+"");

        return builder.build().toString();
    }

    public static String getURLForMovieDetailsWithID(int id){
        Uri.Builder builder= new Uri.Builder();

        builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath(id+"")
                .appendQueryParameter("api_key",Constants.API_KEY)
                .appendQueryParameter("append_to_response","images,videos");

        return builder.build().toString();
    }
}
