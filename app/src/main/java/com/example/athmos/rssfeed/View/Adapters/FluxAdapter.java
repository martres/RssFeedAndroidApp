package com.example.athmos.rssfeed.View.Adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.athmos.rssfeed.Controller.ApiManager;
import com.example.athmos.rssfeed.Model.Feed;
import com.example.athmos.rssfeed.R;
import com.example.athmos.rssfeed.View.AppActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by athmos on 27/01/17.
 */

public class FluxAdapter extends RecyclerView.Adapter<FluxAdapter.FluxViewHolder> {
    private String                  TAG = "Flux Adapter";
    private List<Feed> feedList;
    private AppActivity mActivity;

    public FluxAdapter(List<Feed> flux, AppActivity activity) {
        feedList = new ArrayList<Feed>();
        feedList = flux;
        this.mActivity = activity;
    }

    @Override
    public int                      getItemCount() {
        if (feedList == null) {
            return 0;
        }
        return feedList.size();
    }

    @Override
    public void                     onBindViewHolder(FluxViewHolder viewHolder, int i) {
        Feed feed = feedList.get(i);
        System.out.println("COUCU" + feed.getUrl());
        viewHolder.Flux.setText(feed.getUrl());
        viewHolder.Layout.setOnClickListener(onClickLienFlux(feed, mActivity));
    }
    private View.OnClickListener onClickLienFlux(final Feed lien, final AppActivity activity) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "J'ai cliqué sur un lien");
                ApiManager.getInstance(activity).getRSSContent(lien, activity);
            }
        };
    }
    @Override
    public FluxViewHolder           onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.flux_card, viewGroup, false);

        return new FluxViewHolder(itemView);
    }

    public class FluxViewHolder extends RecyclerView.ViewHolder {
        public TextView         Flux;
        public RelativeLayout   Layout;

        public FluxViewHolder(View v) {
            super(v);
            Flux = (TextView) v.findViewById(R.id.rss);
            Layout = (RelativeLayout) v.findViewById(R.id.CardLayout);
        }
    }

}

