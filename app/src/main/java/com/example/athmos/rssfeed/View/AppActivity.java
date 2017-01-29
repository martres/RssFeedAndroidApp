package com.example.athmos.rssfeed.View;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.*;
import com.example.athmos.rssfeed.Controller.ApiManager;
import com.example.athmos.rssfeed.Controller.Singleton;
import com.example.athmos.rssfeed.Model.Feed;
import com.example.athmos.rssfeed.Model.FeedItem;
import com.example.athmos.rssfeed.R;
import com.example.athmos.rssfeed.View.Adapters.FeedAdapter;
import com.example.athmos.rssfeed.View.Adapters.FluxAdapter;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by athmos on 26/01/17.
 */

public class AppActivity extends AppCompatActivity {
    Singleton                       singleton;
    private MenuAdapter             Menu;
    private ListView                MenuList;
    private TextView                Email;
    private String                  EmailTxt;
    private RecyclerView            RecyclerFeed;
    private RelativeLayout          LayoutAdd;
    private EditText urlEdit;
    private Button   addUrl;
    private RelativeLayout          LayoutGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_activity);
        MenuList = (ListView) findViewById(R.id.Menu);
        Menu = new MenuAdapter(this, MenuList);
        singleton = Singleton.getInstance();
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.MESSAGE);
        initXml();
        EmailTxt = message;
        Email.setText(EmailTxt);
    }

    private void initXml() {
        Email = (TextView) findViewById(R.id.EmailUser);
        RecyclerFeed = (RecyclerView) findViewById(R.id.FeedRecycler);
        LayoutAdd = (RelativeLayout) findViewById(R.id.LayoutAdd);
        LayoutGet = (RelativeLayout) findViewById(R.id.LayoutGet);
        urlEdit = (EditText) findViewById(R.id.Lien);
        addUrl = (Button) findViewById(R.id.SendFlux);
        addUrl.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                if (urlEdit.length() > 10) {
                    int SDK_INT = android.os.Build.VERSION.SDK_INT;
                    if (SDK_INT > 8 && goodUrl(urlEdit.getText().toString()))
                    {
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                                .permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                        try {
                            ApiManager.getInstance(v.getContext()).pushFlux(AppActivity.this, urlEdit.getText().toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    public boolean goodUrl(String address) {
        boolean ok = false;
        try{
            URL feedUrl = null;
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed sf = null;
            feedUrl = new URL(address);
            sf = input.build(new com.sun.syndication.io.XmlReader(feedUrl));
            ok = true;
        } catch (Exception exc){
            exc.printStackTrace();
        }
        return ok;
    }

    public void onClickDeco() {
        Email.setText("");
        EmailTxt = "";
        Intent intent = new Intent(AppActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void onClickAddFlux() {
        LayoutAdd.setVisibility(View.VISIBLE);
        LayoutGet.setVisibility(View.INVISIBLE);
    }

    public void onClickGetFlux() {
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            try {
                ApiManager.getInstance(this).getFlux(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void onResponseGetFlux(List<Feed> flux) {
        FluxAdapter adapter = new FluxAdapter(flux, this);
        RecyclerFeed.setAdapter(adapter);
        RecyclerFeed.setLayoutManager(new GridLayoutManager(this, 1));
        LayoutAdd.setVisibility(View.GONE);
        LayoutGet.setVisibility(View.VISIBLE);
    }

    public void parseRssContent(String response) {
        if (response != "")
        {
            Feed feed = new Feed();
            List<FeedItem> feeditems = new ArrayList<>();
            try {
                InputStream is = new ByteArrayInputStream(response.getBytes("UTF-8"));
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(is);
                Element element=doc.getDocumentElement();
                element.normalize();
                NodeList nList = doc.getElementsByTagName("item");

                for (int i=0 ; i < nList.getLength() ; i++) {
                    Node node = nList.item(i);
                    FeedItem item = new FeedItem();
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element2 = (Element) node;
                        item.setTitle(getValue("title", element2));
                        item.setDescription(getValue("description", element2));
                        item.setDate(getValue("pubDate", element2));
                        item.setLink(getValue("link", element2));
                        feeditems.add(item);
                        System.out.println(getValue("title", element2));
                    }
                }
                feed.setFeeds(feeditems);
                setFeedView(feed);
            } catch (Exception e) {e.printStackTrace();}
        }
        else
        {
            onClickAddFlux();
        }
    }

    private void setFeedView(Feed feed) {
        FeedAdapter adapter = new FeedAdapter(feed.getFeeds(), this);
        RecyclerFeed.setAdapter(adapter);
        RecyclerFeed.setLayoutManager(new GridLayoutManager(this, 1));
    }

    private static String getValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        if (node == null)
            return "";
        return node.getNodeValue();
    }

    public void onResponseAddLink(boolean b) {
        if (b == true)
            onClickGetFlux();
    }
}
