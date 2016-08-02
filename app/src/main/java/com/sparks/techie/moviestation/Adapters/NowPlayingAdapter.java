package com.sparks.techie.moviestation.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.sparks.techie.moviestation.Model.ResultsNowPlaying;
import com.sparks.techie.moviestation.R;
import com.sparks.techie.moviestation.Util.Constants;
import com.sparks.techie.moviestation.Util.VolleyTon;

import java.util.ArrayList;

/**
 * Created by Sailesh on 7/31/16.
 */

public class NowPlayingAdapter extends RecyclerView.Adapter<NowPlayingAdapter.ViewHolder> {
    private ArrayList<ResultsNowPlaying> resultsNowPlaying;

public NowPlayingAdapter(ArrayList<ResultsNowPlaying> resultsNowPlaying){
    this.resultsNowPlaying=resultsNowPlaying;
}
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.now_playing_recycle_view_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if(resultsNowPlaying!=null) {
            holder.nowPlayingTextView.setText(resultsNowPlaying.get(position).getTitle());

            String poster_path = resultsNowPlaying.get(position).getPoster_path();
            String url = Constants.IMAGE_BASE_URL + poster_path;
            ImageLoader imageLoader = VolleyTon.getInstance().getImageLoader();

            holder.nowPlayingImageView.setImageUrl(url, imageLoader);
        }
    }

    @Override
    public int getItemCount() {
        return resultsNowPlaying.size();
    }

    public void updateNowPlaying(ArrayList<ResultsNowPlaying> resultsNowPlaying){
        this.resultsNowPlaying.addAll(resultsNowPlaying);
        notifyDataSetChanged();
    }
    static class ViewHolder extends RecyclerView.ViewHolder {

        private NetworkImageView nowPlayingImageView;
        private TextView nowPlayingTextView;

        ViewHolder(View itemView) {
            super(itemView);
            nowPlayingTextView =(TextView)itemView.findViewById(R.id.nowPlayingTextView);
            nowPlayingImageView =(NetworkImageView) itemView.findViewById(R.id.nowPlayingImageView);
        }

    }
}
