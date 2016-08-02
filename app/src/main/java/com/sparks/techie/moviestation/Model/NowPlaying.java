package com.sparks.techie.moviestation.Model;

import java.util.ArrayList;

/**
 * Created by Sailesh on 8/1/16.
 */

public class NowPlaying {
    private String page;
    private ArrayList<ResultsNowPlaying> results;
   // private Dates dates;


    public String getPage() {
        return page;
    }

    public ArrayList<ResultsNowPlaying> getResults() {
        return results;
    }
}
