package com.sparks.techie.moviestation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.sparks.techie.moviestation.Adapters.EndlessRecyclerOnScrollListener;
import com.sparks.techie.moviestation.Adapters.NowPlayingAdapter;
import com.sparks.techie.moviestation.Model.NowPlaying;
import com.sparks.techie.moviestation.Model.ResultsNowPlaying;
import com.sparks.techie.moviestation.Util.Constants;
import com.sparks.techie.moviestation.Util.UriBuilderUtil;
import com.sparks.techie.moviestation.Util.VolleyTon;

import java.util.ArrayList;

public class Home extends BaseActivity {

    private RecyclerView mRecyclerView;
    private NowPlayingAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private int totalNumberOfPages=0;
    private MovieStationOnClickListener movieStationOnClickListener;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mRecyclerView = (RecyclerView) findViewById(R.id.latest_movies_carousel);
        progressBar = (ProgressBar) findViewById(R.id.nowPlayingProgressBar);
        progressBar.setIndeterminate(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        movieStationOnClickListener = new MovieStationOnClickListener() {
            @Override
            public void onClick(int position, ResultsNowPlaying resultsNowPlaying) {
                Intent intent = new Intent(Home.this,MovieDetailsActivity.class);
                intent.putExtra(Constants.MOVIE_ID,resultsNowPlaying.getId());
                startActivity(intent);
            }
        };
        mAdapter = new NowPlayingAdapter(new ArrayList<ResultsNowPlaying>(),movieStationOnClickListener);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(mLayoutManager) {
            @Override
            protected void loadMore(int pageNumber) {
                if(pageNumber!=totalNumberOfPages){
                    fetchLatestMovieData(pageNumber);
                }
            }
        });

        fetchLatestMovieData(1);
    }

    private void fetchLatestMovieData(int pageNumber) {
        String url = UriBuilderUtil.getURLForNowPlayingWithPageNumber(pageNumber);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson= new Gson();
                NowPlaying nowPlaying= gson.fromJson(response,NowPlaying.class);
                mAdapter.updateNowPlaying(nowPlaying.getResults());
                totalNumberOfPages=nowPlaying.getTotal_pages();
                progressBar.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);

            }
        });
        VolleyTon.getInstance().addToRequestQueue(stringRequest);
    }

    public interface MovieStationOnClickListener{
     void onClick(int position,ResultsNowPlaying resultsNowPlaying);
    }
}
