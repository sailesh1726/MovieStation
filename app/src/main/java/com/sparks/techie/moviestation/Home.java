package com.sparks.techie.moviestation;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.sparks.techie.moviestation.Adapters.NowPlayingAdapter;
import com.sparks.techie.moviestation.Model.NowPlaying;
import com.sparks.techie.moviestation.Util.Constants;
import com.sparks.techie.moviestation.Util.VolleyTon;

public class Home extends BaseActivity {

    private RecyclerView mRecyclerView;
    private NowPlayingAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mRecyclerView = (RecyclerView) findViewById(R.id.latest_movies_carousel);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        fetchLatestMovieData();
    }

    private void fetchLatestMovieData() {
        Uri.Builder builder= new Uri.Builder();

        builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath("now_playing")
                .appendQueryParameter("api_key",Constants.API_KEY);
        String url=builder.build().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson= new Gson();
                NowPlaying nowPlaying= gson.fromJson(response,NowPlaying.class);
                mAdapter= new NowPlayingAdapter(nowPlaying.getResults());
                mRecyclerView.setAdapter(mAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getNetworkTimeMs();
            }
        });
        VolleyTon.getInstance().addToRequestQueue(stringRequest);
    }
}
