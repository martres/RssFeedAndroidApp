package com.example.athmos.rssfeed.View.Adapters;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.athmos.rssfeed.Model.FeedItem;
import com.example.athmos.rssfeed.R;
import com.example.athmos.rssfeed.View.AppActivity;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by athmos on 27/01/17.
 */

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder> {
    private String                  TAG = "Flux Adapter";
    private List<FeedItem> Feeds;
    private AppActivity mActivity;

    public FeedAdapter(List<FeedItem> feeds, AppActivity activity) {
        Feeds = new ArrayList<FeedItem>();
        Feeds = feeds;
        this.mActivity = activity;
    }

    @Override
    public int                      getItemCount() {
        return Feeds.size();
    }

    @Override
    public void                     onBindViewHolder(FeedAdapter.FeedViewHolder viewHolder, int i) {
        FeedItem feed = Feeds.get(i);
        try {
            viewHolder.TitleFeed.setText(StringEscapeUtils.unescapeHtml3(new String(Html.fromHtml(feed.getTitle()).toString().getBytes("ISO-8859-1"), "UTF-8")));
            viewHolder.DescriptionFeed.setText(StringEscapeUtils.unescapeHtml3(new String(Html.fromHtml(feed.getDescription()   ).toString().getBytes("ISO-8859-1"), "UTF-8")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        viewHolder.DateFeed.setText(feed.getDate());
    }

    @Override
    public FeedAdapter.FeedViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.feed_card, viewGroup, false);

        return new FeedAdapter.FeedViewHolder(itemView);
    }

    public class FeedViewHolder extends RecyclerView.ViewHolder {
        public TextView TitleFeed,DescriptionFeed,DateFeed;
        public RelativeLayout Layout;

        public FeedViewHolder(View v) {
            super(v);
            TitleFeed = (TextView) v.findViewById(R.id.TitleFeed);
            DescriptionFeed = (TextView) v.findViewById(R.id.DescriptionFeed);
            DateFeed = (TextView) v.findViewById(R.id.DateFeed);
            Layout = (RelativeLayout) v.findViewById(R.id.CardLayout);
        }
    }

}