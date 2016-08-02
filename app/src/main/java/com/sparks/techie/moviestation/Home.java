package com.sparks.techie.moviestation;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.sparks.techie.moviestation.Adapters.EndlessRecyclerOnScrollListener;
import com.sparks.techie.moviestation.Adapters.NowPlayingAdapter;
import com.sparks.techie.moviestation.Model.NowPlaying;
import com.sparks.techie.moviestation.Model.ResultsNowPlaying;
import com.sparks.techie.moviestation.Util.UriBuilderUtil;
import com.sparks.techie.moviestation.Util.VolleyTon;

import java.util.ArrayList;

public class Home extends BaseActivity {

    private RecyclerView mRecyclerView;
    private NowPlayingAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private int totalNumberOfPages=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mRecyclerView = (RecyclerView) findViewById(R.id.latest_movies_carousel);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new NowPlayingAdapter(new ArrayList<ResultsNowPlaying>());

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
