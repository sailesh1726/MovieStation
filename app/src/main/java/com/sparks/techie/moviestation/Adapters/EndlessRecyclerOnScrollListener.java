package com.sparks.techie.moviestation.Adapters;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Sailesh on 8/2/16.
 */

public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {
    private LinearLayoutManager linearLayoutManager;
    private boolean loading = true;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private int previousTotal=0;
    private int visibleThreshold = 5;
    private int pageNumber =1;

    protected EndlessRecyclerOnScrollListener(LinearLayoutManager linearLayoutManager){
        this.linearLayoutManager=linearLayoutManager;
    }
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if(dx > 0) //check for scroll down
        {
            visibleItemCount = linearLayoutManager.getChildCount();
            totalItemCount = linearLayoutManager.getItemCount();
            pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();

            if (loading)
            {
                if(totalItemCount>previousTotal){
                    loading = false;
                    previousTotal = totalItemCount;
                }

            }
            if (!loading &&  (totalItemCount-visibleItemCount) <= (pastVisiblesItems+visibleThreshold))
            {
                pageNumber++;
                loadMore(pageNumber);
                loading = true;
            }
        }
    }

    protected abstract void loadMore(int pageNumber);
}
