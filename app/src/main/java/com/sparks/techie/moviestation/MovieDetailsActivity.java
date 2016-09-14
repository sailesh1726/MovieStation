package com.sparks.techie.moviestation;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.gson.Gson;
import com.sparks.techie.moviestation.Model.MovieDetails;
import com.sparks.techie.moviestation.Util.Constants;
import com.sparks.techie.moviestation.Util.UriBuilderUtil;
import com.sparks.techie.moviestation.Util.VolleyTon;

public class MovieDetailsActivity extends YouTubeBaseActivity {

    private NetworkImageView networkImageView;
    private YouTubePlayerView youTubePlayerView;
    private int movieId;
    private MovieDetails movieDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        networkImageView= (NetworkImageView) findViewById(R.id.movieDetailsPoster);
        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.trailerYoutube);

        Bundle myBundle = getIntent().getExtras();
        if(myBundle!=null)
            movieId=Integer.parseInt(myBundle.getString(Constants.MOVIE_ID));
        fetchDataForMovieDetails(movieId);
        ImageLoader imageLoader = VolleyTon.getInstance().getImageLoader();

        //networkImageView.setImageUrl(url, imageLoader);
        networkImageView.setErrorImageResId(R.drawable.now_playing_place_holder);
    }

    private void fetchDataForMovieDetails(int movieId) {
        String url = UriBuilderUtil.getURLForMovieDetailsWithID(movieId);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson= new Gson();
                movieDetails = gson.fromJson(response,MovieDetails.class);
                String poster_path= movieDetails.getPoster_path();

                String url = Constants.IMAGE_BASE_URL + poster_path;
                ImageLoader imageLoader = VolleyTon.getInstance().getImageLoader();
                youTubePlayerView.initialize(Constants.YOUBUE_API_KEY, new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                        if (!b) {

                            // loadVideo() will auto play video
                            // Use cueVideo() method, if you don't want to play it automatically
                            youTubePlayer.loadVideo(movieDetails.getVideos().getResults().get(0).getKey());

                            // Hiding player controls
                            youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
                        }
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });

                networkImageView.setImageUrl(url, imageLoader);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        VolleyTon.getInstance().addToRequestQueue(stringRequest);
    }
}
